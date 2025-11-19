package net.safedata.microservices.training.order.service;

import net.safedata.microservices.training.dto.order.OrderDTO;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import net.safedata.microservices.training.message.command.order.CreateOrderCommand;
import net.safedata.microservices.training.message.event.customer.CustomerCreatedEvent;
import net.safedata.microservices.training.message.event.customer.CustomerUpdatedEvent;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import net.safedata.microservices.training.message.event.order.OrderDeliveredEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;
import net.safedata.microservices.training.order.domain.model.Order;
import net.safedata.microservices.training.order.domain.model.OrderItem;
import net.safedata.microservices.training.order.domain.model.OrderStatus;
import net.safedata.microservices.training.order.inbound.port.MessagingInboundPort;
import net.safedata.microservices.training.order.inbound.port.RestInboundPort;
import net.safedata.microservices.training.order.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.order.outbound.port.PersistenceOutboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService implements RestInboundPort, MessagingInboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private static final Random RANDOM = new Random(3000);

    // Sequential ID generators for realistic ID generation
    private static final AtomicLong MESSAGE_ID_COUNTER = new AtomicLong(1000);
    private static final AtomicLong EVENT_ID_COUNTER = new AtomicLong(5000);

    private final MessagingOutboundPort messagingOutboundPort;
    private final PersistenceOutboundPort persistenceOutboundPort;

    private final ThreadPoolTaskExecutor customThreadPool;

    @Autowired
    public OrderService(final MessagingOutboundPort messagingOutboundPort,
                        final PersistenceOutboundPort persistenceOutboundPort,
                        final ThreadPoolTaskExecutor customThreadPool) {
        this.messagingOutboundPort = messagingOutboundPort;
        this.persistenceOutboundPort = persistenceOutboundPort;
        this.customThreadPool = customThreadPool;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveInitialOrder() {
        Order order = new Order(1, RANDOM.nextInt(200));
        order.setStatus(OrderStatus.PAYED);

        OrderItem orderItem = new OrderItem();
        orderItem.setRestaurantId(RANDOM.nextInt(100));
        orderItem.setFoodId(RANDOM.nextLong(150));
        orderItem.setPrice(RANDOM.nextLong(200));
        orderItem.setName("Great pizza");
        orderItem.setDescription("A delicious pizza");

        order.setOrderItems(Set.of(orderItem));
        orderItem.setOrder(order);
        persistenceOutboundPort.save(order);
        LOGGER.info("The initial order was saved");
    }

    // creating an order received from a REST endpoint (UI, testing app etc)
    @Transactional
    public void createOrder(final OrderDTO orderDTO) {
        final long customerId = orderDTO.customerId();
        final long orderId = saveOrder(convertDTOIntoOrder(orderDTO));

        LOGGER.info("Creating an order for the customer with the ID {}, for {} items...", customerId,
                orderDTO.orderItems().size());
        final double orderTotal = orderDTO.getOrderTotal();

        CompletableFuture.runAsync(() -> publishChargeOrder(customerId, orderId, orderTotal))
                         .thenRunAsync(() -> publishOrderCreatedEvent(customerId, orderId));
    }

    // creating an order received from a messaging endpoint (upstream system, 3rd party application etc)
    @Transactional
    public void createOrder(final CreateOrderCommand createOrderCommand) {
        final long customerId = createOrderCommand.getCustomerId();
        LOGGER.info("Creating an order for the product '{}', for the customer with the ID {}...",
                createOrderCommand.getProductName(), customerId);

        final long orderId = saveOrder(convertCommandIntoOrder(createOrderCommand));
        final double orderTotal = createOrderCommand.getOrderTotal();

        CompletableFuture.runAsync(() -> publishChargeOrder(customerId, orderId, orderTotal), customThreadPool)
                         .thenRunAsync(() -> publishOrderCreatedEvent(customerId, orderId), customThreadPool)
                         .thenRunAsync(() -> LOGGER.info("The command and event were successfully published"));
    }

    @Transactional
    public void handleCustomerUpdated(final CustomerUpdatedEvent customerUpdatedEvent) {
        LOGGER.info("Updating the orders for the customer with the ID {}...", customerUpdatedEvent.getCustomerId());

        // TODO insert magic here
    }

    @Transactional
    public void handleOrderCharged(final OrderChargedEvent orderChargedEvent) {
        final long customerId = orderChargedEvent.getCustomerId();
        final long orderId = orderChargedEvent.getOrderId();
        LOGGER.info("The order with the ID {} of the customer {} was successfully charged, updating it", orderId, customerId);

        // Update order status to PAYED
        Optional<Order> orderOpt = persistenceOutboundPort.findById(orderId);
        if (orderOpt.isEmpty()) {
            LOGGER.warn("Received OrderChargedEvent for non-existent order ID {}. " +
                    "Skipping gracefully (possibly deleted, out-of-order, or stale message)", orderId);
            return;
        }

        Order order = orderOpt.get();
        order.setStatus(OrderStatus.PAYED);
        persistenceOutboundPort.save(order);
        LOGGER.info("Order {} status updated to PAYED - order will be processed by restaurant", orderId);

        // Note: CustomerService will handle initiating restaurant processing via ProcessOrderCommand
    }

    @Transactional
    public void handleOrderNotCharged(final OrderNotChargedEvent orderNotChargedEvent) {
        final long orderId = orderNotChargedEvent.getOrderId();
        final long customerId = orderNotChargedEvent.getCustomerId();
        LOGGER.warn("The order with the ID {} of the customer {} could not be charged - reason: '{}'",
                orderId, customerId, orderNotChargedEvent.getReason());

        // Update order status to PAYMENT_FAILED
        Optional<Order> orderOpt = persistenceOutboundPort.findById(orderId);
        if (orderOpt.isEmpty()) {
            LOGGER.warn("Received OrderNotChargedEvent for non-existent order ID {}. " +
                    "Skipping gracefully (possibly deleted, out-of-order, or stale message)", orderId);
            return;
        }

        Order order = orderOpt.get();
        order.setStatus(OrderStatus.PAYMENT_FAILED);
        persistenceOutboundPort.save(order);
        LOGGER.warn("Order {} status updated to PAYMENT_FAILED", orderId);
    }

    @Transactional
    public void handleOrderProcessed(final OrderProcessedEvent orderProcessedEvent) {
        final long customerId = orderProcessedEvent.getCustomerId();
        final long orderId = orderProcessedEvent.getOrderId();
        LOGGER.info("The order with the ID {} of the customer {} was processed by the restaurant", orderId, customerId);

        // Update order status to PROCESSED
        Optional<Order> orderOpt = persistenceOutboundPort.findById(orderId);
        if (orderOpt.isEmpty()) {
            LOGGER.warn("Received OrderProcessedEvent for non-existent order ID {}. " +
                    "Skipping gracefully (possibly deleted, out-of-order, or stale message)", orderId);
            return;
        }

        Order order = orderOpt.get();
        order.setStatus(OrderStatus.PROCESSED);
        persistenceOutboundPort.save(order);
        LOGGER.info("Order {} status updated to PROCESSED", orderId);
    }

    @Transactional
    public void handleOrderDelivered(final OrderDeliveredEvent orderDeliveredEvent) {
        final long customerId = orderDeliveredEvent.getCustomerId();
        final long orderId = orderDeliveredEvent.getOrderId();
        LOGGER.info("The order with the ID {} of the customer {} was successfully delivered!", orderId, customerId);

        // Update order status to DELIVERED
        Optional<Order> orderOpt = persistenceOutboundPort.findById(orderId);
        if (orderOpt.isEmpty()) {
            LOGGER.warn("Received OrderDeliveredEvent for non-existent order ID {}. " +
                    "Skipping gracefully (possibly deleted, out-of-order, or stale message)", orderId);
            return;
        }

        Order order = orderOpt.get();
        order.setStatus(OrderStatus.DELIVERED);
        persistenceOutboundPort.save(order);
        LOGGER.info("Order {} status updated to DELIVERED - order lifecycle complete!", orderId);
    }

    @Transactional
    public void handleCustomerCreated(final CustomerCreatedEvent customerCreatedEvent) {
        final long customerId = customerCreatedEvent.getCustomerId();
        final String customerEmail = customerCreatedEvent.getCustomerEmail();
        LOGGER.info("New customer created with ID {} and email '{}' - order service is now aware of this customer",
                customerId, customerEmail);

        // demonstrates event-driven awareness; in a real system, we may cache the customer data or perform validations
        LOGGER.debug("Order service can now accept orders for customer {}", customerId);
    }

    private void publishChargeOrder(final long customerId, final long orderId, final double orderTotal) {
        messagingOutboundPort.publishChargeOrderCommand(
                new ChargeOrderCommand(getNextMessageId(), customerId, orderId, orderTotal,
                        ChargeOrderCommand.Currency.EUR));
    }

    private void publishOrderCreatedEvent(final long customerId, final long orderId) {
        messagingOutboundPort.publishOrderCreatedEvent(
                new OrderCreatedEvent(getNextMessageId(), getNextEventId(), customerId, orderId));
    }

    private Order convertCommandIntoOrder(final CreateOrderCommand command) {
        return new Order(command.getCustomerId(), command.getOrderTotal());
    }

    private Order convertDTOIntoOrder(final OrderDTO orderDTO) {
        return new Order(orderDTO.customerId(), orderDTO.getOrderTotal());
    }

    private long saveOrder(final Order order) {
        sleepALittle();
        return persistenceOutboundPort.save(order);
    }

    private long getNextMessageId() {
        return MESSAGE_ID_COUNTER.incrementAndGet();
    }

    private long getNextEventId() {
        // returned from the saved database event, before sending it (using transactional messaging)
        return EVENT_ID_COUNTER.incrementAndGet();
    }

    // simulate a long running operation
    private void sleepALittle() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
