package net.safedata.microservices.training.customer.inbound.adapter;

import net.safedata.microservices.training.customer.inbound.port.CustomerService;
import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderShippedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private final CustomerService customerService;

    @Autowired
    public MessageConsumer(final CustomerService customerService) {
        this.customerService = customerService;
    }

    public void orderCreated(final OrderCreatedEvent orderCreatedEvent) {
        LOGGER.debug("Received a '{}' event, the orderId is {}, the customer ID is {}",
                orderCreatedEvent.getName(), orderCreatedEvent.getOrderId(), orderCreatedEvent.getCustomerId());

        customerService.handleOrderCreated(orderCreatedEvent);
    }

    public void orderCharged(final OrderChargedEvent orderChargedEvent) {
        LOGGER.debug("Received a '{}' event, the ID of the customer is {}",
                orderChargedEvent.getName(), orderChargedEvent.getCustomerId());

        customerService.handleOrderCharged(orderChargedEvent);
    }

    public void orderNotCharged(final OrderNotChargedEvent orderNotChargedEvent) {
        LOGGER.warn("Received a '{}' event for the order {} of the customer {}",
                orderNotChargedEvent.getName(), orderNotChargedEvent.getOrderId(), orderNotChargedEvent.getCustomerId());

        customerService.handleOrderNotCharged(orderNotChargedEvent);
    }

    public void orderShipped(final OrderShippedEvent orderShippedEvent) {
        LOGGER.warn("Received a '{}' event for the order {} of the customer {}",
                orderShippedEvent.getName(), orderShippedEvent.getOrderId(), orderShippedEvent.getCustomerId());

        customerService.handleOrderShipped(orderShippedEvent);
    }
}
