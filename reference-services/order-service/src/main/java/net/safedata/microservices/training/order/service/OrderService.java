package net.safedata.microservices.training.order.service;

import net.safedata.microservices.training.order.dto.OrderDTO;
import net.safedata.microservices.training.order.events.CustomerUpdatedEvent;
import net.safedata.microservices.training.order.message.CreateOrderMessage;
import net.safedata.microservices.training.order.events.OrderCreatedEvent;
import net.safedata.microservices.training.order.marker.InboundPort;
import net.safedata.microservices.training.order.ports.MessagingOutboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class OrderService implements InboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final MessagingOutboundPort messagingOutboundPort;

    @Autowired
    public OrderService(final MessagingOutboundPort messagingOutboundPort) {
        this.messagingOutboundPort = messagingOutboundPort;
    }

    // creating an order received from a REST endpoint (UI, testing app etc)
    @Transactional
    public void createOrder(final OrderDTO orderDTO) {
        LOGGER.info("Creating an order for {} items...", orderDTO.getOrderItems()
                                                                 .size());

        final long orderId = createOrder();

        messagingOutboundPort.publishEvent(
                new OrderCreatedEvent(getNextEventId(), orderDTO.getCustomerId(), orderId));
    }

    // creating an order received from a messaging endpoint (upstream system, 3rd party application etc)
    @Transactional
    public void createOrder(final CreateOrderMessage createOrderMessage) {
        LOGGER.info("Creating an order for the product '{}', for the customer with the ID {}...",
                createOrderMessage.getProductName(), createOrderMessage.getCustomerId());

        final long orderId = createOrder();

        messagingOutboundPort.publishEvent(
                new OrderCreatedEvent(getNextEventId(), createOrderMessage.getCustomerId(), orderId));
    }

    private long createOrder() {
        // TODO insert magic here
        sleepALittle();

        return new Random(10000).nextLong();
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

    public void customerUpdated(final CustomerUpdatedEvent customerUpdatedEvent) {
        LOGGER.info("Updating the orders for the customer with the ID {}...", customerUpdatedEvent.getCustomerId());
    }
}
