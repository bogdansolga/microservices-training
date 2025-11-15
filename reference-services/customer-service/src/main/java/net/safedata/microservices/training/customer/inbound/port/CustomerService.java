package net.safedata.microservices.training.customer.inbound.port;

import net.safedata.microservices.training.customer.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.dto.customer.CustomerDTO;
import net.safedata.microservices.training.marker.port.InboundPort;
import net.safedata.microservices.training.message.command.order.ProcessOrderCommand;
import net.safedata.microservices.training.message.event.customer.CustomerCreatedEvent;
import net.safedata.microservices.training.message.event.customer.CustomerUpdatedEvent;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import net.safedata.microservices.training.message.event.order.OrderDeliveredEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CustomerService implements InboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    // Sequential ID generators for realistic ID generation
    private static final AtomicLong CUSTOMER_ID_COUNTER = new AtomicLong(100);
    private static final AtomicLong MESSAGE_ID_COUNTER = new AtomicLong(2000);
    private static final AtomicLong EVENT_ID_COUNTER = new AtomicLong(6000);

    private final MessagingOutboundPort messagingOutboundPort;

    @Autowired
    public CustomerService(final MessagingOutboundPort messagingOutboundPort) {
        this.messagingOutboundPort = messagingOutboundPort;
    }

    @Transactional
    public void createCustomer(final CustomerDTO customerDTO) {
        LOGGER.info("Creating the customer '{}' [{}]...", customerDTO.getName(), customerDTO.getEmail());

        final long customerId = saveCustomerInTheDatabase(customerDTO);

        // insert magic here
        messagingOutboundPort.publishCustomerCreatedEvent(
                new CustomerCreatedEvent(getNextMessageId(), getNextEventId(), customerId, customerDTO.getEmail()));
    }

    private long saveCustomerInTheDatabase(final CustomerDTO customerDTO) {
        // TODO insert magic here
        sleepALittle();

        return CUSTOMER_ID_COUNTER.incrementAndGet();
    }

    @Transactional
    public void handleOrderCreated(final OrderCreatedEvent orderCreatedMessage) {
        final long customerId = orderCreatedMessage.getCustomerId();

        LOGGER.info("Updating the customer with the ID {}...", customerId);
        updateCustomer(customerId);

        //TODO insert magic here

        messagingOutboundPort.publishCustomerUpdatedEvent(
                new CustomerUpdatedEvent(getNextMessageId(), getNextEventId(), customerId));
    }

    @Transactional
    public void handleOrderCharged(final OrderChargedEvent orderChargedEvent) {
        LOGGER.info("The order with the ID {} of the customer {} was successfully charged, updating it",
                orderChargedEvent.getOrderId(), orderChargedEvent.getCustomerId());

        // TODO insert magic here

        messagingOutboundPort.publishProcessOrderCommand(
                new ProcessOrderCommand(getNextMessageId(), getNextEventId(), "something", 220));
    }

    @Transactional
    public void handleOrderNotCharged(final OrderNotChargedEvent orderNotChargedEvent) {
        LOGGER.warn("The order with the ID {} of the customer {} could not be charged - reason: '{}'",
                orderNotChargedEvent.getOrderId(), orderNotChargedEvent.getCustomerId(), orderNotChargedEvent.getReason());

        // TODO insert magic here
    }

    @Transactional
    public void handleOrderProcessed(final OrderProcessedEvent orderProcessedEvent) {
        final long customerId = orderProcessedEvent.getCustomerId();
        final long orderId = orderProcessedEvent.getOrderId();
        LOGGER.info("The order {} for customer {} has been processed by the restaurant", orderId, customerId);

        // track order progress in customer's order history
        LOGGER.debug("Customer {} can see that order {} is being prepared", customerId, orderId);
    }

    @Transactional
    public void handleOrderDelivered(final OrderDeliveredEvent orderDeliveredEvent) {
        final long customerId = orderDeliveredEvent.getCustomerId();
        final long orderId = orderDeliveredEvent.getOrderId();
        LOGGER.info("Order {} for customer {} has been successfully delivered!", orderId, customerId);

        // update customer order history, send delivery confirmation
        LOGGER.debug("Customer {} notified of successful delivery for order {}", customerId, orderId);
    }

    private void updateCustomer(long customerId) {
        // TODO insert magic here
        sleepALittle();
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
