package net.safedata.microservices.training.order.service;

import net.safedata.microservices.training.order.domain.model.Order;
import net.safedata.microservices.training.order.dto.OrderDTO;
import net.safedata.microservices.training.order.marker.port.InboundPort;
import net.safedata.microservices.training.order.message.event.*;
import net.safedata.microservices.training.order.message.command.CreateOrderCommand;
import net.safedata.microservices.training.order.ports.MessagingOutboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService implements InboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private static final AtomicLong ATOMIC_LONG = new AtomicLong(100);

    private final MessagingOutboundPort messagingOutboundPort;

    @Autowired
    public OrderService(final MessagingOutboundPort messagingOutboundPort) {
        this.messagingOutboundPort = messagingOutboundPort;
    }

    // creating an order received from a REST endpoint (UI, testing app etc)
    @Transactional
    public void createOrder(final OrderDTO orderDTO) {
        final long customerId = orderDTO.getCustomerId();
        final long orderId = saveOrder(convertDTOIntoOrder(orderDTO));

        LOGGER.info("Creating an order for the customer with the ID {}, for {} items...", customerId,
                orderDTO.getOrderItems().size());

        messagingOutboundPort.publishOrderCreatedEvent(
                new OrderCreatedEvent(getNextMessageId(), getNextEventId(), customerId, orderId));
    }

    // creating an order received from a messaging endpoint (upstream system, 3rd party application etc)
    @Transactional
    public void createOrder(final CreateOrderCommand createOrderCommand) {
        final long customerId = createOrderCommand.getCustomerId();
        LOGGER.info("Creating an order for the product '{}', for the customer with the ID {}...",
                createOrderCommand.getProductName(), customerId);

        final long orderId = saveOrder(convertCommandIntoOrder(createOrderCommand));

        messagingOutboundPort.publishOrderCreatedEvent(
                new OrderCreatedEvent(getNextMessageId(), getNextEventId(), customerId, orderId));
    }

    @Transactional
    public void handleCustomerUpdated(final CustomerUpdatedEvent customerUpdatedEvent) {
        LOGGER.info("Updating the orders for the customer with the ID {}...", customerUpdatedEvent.getCustomerId());

        // TODO insert magic here
    }

    private Order convertCommandIntoOrder(final CreateOrderCommand command) {
        return new Order(command.getCustomerId(), command.getOrderTotal());
    }

    private Order convertDTOIntoOrder(final OrderDTO orderDTO) {
        return new Order(orderDTO.getCustomerId(), orderDTO.getOrderTotal());
    }

    private long saveOrder(final Order order) {
        // TODO insert magic here
        sleepALittle();

        return ATOMIC_LONG.incrementAndGet();
    }

    private long getNextMessageId() {
        return new Random(90000).nextLong();
    }

    private long getNextEventId() {
        // returned from the saved database event, before sending it (using transactional messaging)
        return new Random(900000).nextLong();
    }

    // simulate a long running operation
    private void sleepALittle() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
