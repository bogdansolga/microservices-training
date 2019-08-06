package net.safedata.microservices.training.order.inbound.adapter;

import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.marker.message.Channels;
import net.safedata.microservices.training.message.command.order.CreateOrderCommand;
import net.safedata.microservices.training.message.event.customer.CustomerUpdatedEvent;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderShippedEvent;
import net.safedata.microservices.training.order.channel.InboundChannels;
import net.safedata.microservices.training.order.inbound.port.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(InboundChannels.class)
public class MessageConsumer implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private final OrderService orderService;

    @Autowired
    public MessageConsumer(final OrderService orderService) {
        this.orderService = orderService;
    }

    @StreamListener(Channels.Inbound.CREATE_ORDER)
    public void createOrder(final CreateOrderCommand createOrderCommand) {
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        LOGGER.debug("Received a '{}' command, the ordered item is '{}', the customer ID is {}",
                createOrderCommand.getName(), createOrderCommand.getProductName(), createOrderCommand.getCustomerId());

        orderService.createOrder(createOrderCommand);
    }

    @StreamListener(Channels.Inbound.CUSTOMER_UPDATED)
    public void customerUpdated(final CustomerUpdatedEvent customerUpdatedEvent) {
        LOGGER.debug("Received a '{}' event, the ID of the updated customer is {}",
                customerUpdatedEvent.getName(), customerUpdatedEvent.getCustomerId());

        orderService.handleCustomerUpdated(customerUpdatedEvent);
    }

    @StreamListener(Channels.Inbound.ORDER_CHARGED)
    public void orderCharged(final OrderChargedEvent orderChargedEvent) {
        LOGGER.debug("Received a '{}' event for the order {} of the customer {}",
                orderChargedEvent.getName(), orderChargedEvent.getOrderId(), orderChargedEvent.getCustomerId());

        orderService.handleOrderCharged(orderChargedEvent);
    }

    @StreamListener(Channels.Inbound.ORDER_NOT_CHARGED)
    public void orderNotCharged(final OrderNotChargedEvent orderNotChargedEvent) {
        LOGGER.warn("Received a '{}' event for the order {} of the customer {}",
                orderNotChargedEvent.getName(), orderNotChargedEvent.getOrderId(), orderNotChargedEvent.getCustomerId());

        orderService.handleOrderNotCharged(orderNotChargedEvent);
    }

    @StreamListener(Channels.Inbound.ORDER_SHIPPED)
    public void orderShipped(final OrderShippedEvent orderShippedEvent) {
        LOGGER.debug("Received a '{}' event for the order {} of the customer {}",
                orderShippedEvent.getName(), orderShippedEvent.getOrderId(), orderShippedEvent.getCustomerId());

        orderService.handleOrderShipped(orderShippedEvent);
    }
}
