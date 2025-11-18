package net.safedata.microservices.training.customer.inbound.adapter;

import net.safedata.microservices.training.customer.service.CustomerService;
import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import net.safedata.microservices.training.message.event.order.OrderDeliveredEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MessagingInboundAdapter implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingInboundAdapter.class);

    private final CustomerService customerService;

    @Autowired
    public MessagingInboundAdapter(final CustomerService customerService) {
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
    public Consumer<OrderProcessedEvent> orderProcessed() {
        return orderProcessedEvent -> {
            LOGGER.debug("Received a '{}' event for the order {} of the customer {}",
                    orderProcessedEvent.getName(), orderProcessedEvent.getOrderId(), orderProcessedEvent.getCustomerId());

            customerService.handleOrderProcessed(orderProcessedEvent);
        };
    }

    @Bean
    public Consumer<OrderDeliveredEvent> orderDelivered() {
        return orderDeliveredEvent -> {
            LOGGER.debug("Received a '{}' event for the order {} of the customer {}",
                    orderDeliveredEvent.getName(), orderDeliveredEvent.getOrderId(), orderDeliveredEvent.getCustomerId());

            customerService.handleOrderDelivered(orderDeliveredEvent);
        };
    }
}
