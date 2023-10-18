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
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MessageConsumer implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private final CustomerService customerService;

    @Autowired
    public MessageConsumer(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return orderCreatedEvent -> {
            LOGGER.debug("Received a '{}' event, the orderId is {}, the customer ID is {}",
                    orderCreatedEvent.getName(), orderCreatedEvent.getOrderId(), orderCreatedEvent.getCustomerId());

            customerService.handleOrderCreated(orderCreatedEvent);
        };
    }

    @Bean
    public Consumer<OrderChargedEvent> orderCharged() {
        return orderChargedEvent -> {
            LOGGER.debug("Received a '{}' event, the ID of the customer is {}",
                    orderChargedEvent.getName(), orderChargedEvent.getCustomerId());

            customerService.handleOrderCharged(orderChargedEvent);
        };
    }

    @Bean
    public Consumer<OrderNotChargedEvent> orderNotCharged() {
        return orderNotChargedEvent -> {
            LOGGER.warn("Received a '{}' event for the order {} of the customer {}",
                    orderNotChargedEvent.getName(), orderNotChargedEvent.getOrderId(), orderNotChargedEvent.getCustomerId());

            customerService.handleOrderNotCharged(orderNotChargedEvent);
        };
    }

    @Bean
    public Consumer<OrderShippedEvent> orderShipped() {
        return orderShippedEvent -> {
            LOGGER.warn("Received a '{}' event for the order {} of the customer {}",
                    orderShippedEvent.getName(), orderShippedEvent.getOrderId(), orderShippedEvent.getCustomerId());

            customerService.handleOrderShipped(orderShippedEvent);
        };
    }
}
