package net.safedata.microservices.training.order.adapter;

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
public class MessagingInboundAdapter implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingInboundAdapter.class);

    private final OrderService orderService;

    @Autowired
    public MessagingInboundAdapter(final OrderService orderService) {
        this.orderService = orderService;
    }

    @StreamListener(Channels.Commands.CREATE_ORDER)
    public void createOrder(final CreateOrderCommand createOrderCommand) {
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        LOGGER.debug("Received a '{}' command, the ordered item is '{}', the customer ID is {}",
                createOrderCommand.getName(), createOrderCommand.getProductName(), createOrderCommand.getCustomerId());

        orderService.createOrder(createOrderCommand);
    }

    @StreamListener(Channels.Events.CUSTOMER_UPDATED)
    public void customerUpdated(final CustomerUpdatedEvent customerUpdatedEvent) {
        LOGGER.debug("Received a '{}' event, the ID of the updated customer is {}",
                customerUpdatedEvent.getName(), customerUpdatedEvent.getCustomerId());

        orderService.handleCustomerUpdated(customerUpdatedEvent);
    }

    @StreamListener(Channels.Events.ORDER_CHARGED)
    public void orderCharged(final OrderChargedEvent orderChargedEvent) {
        LOGGER.debug("Received a '{}' event for the order {} of the customer {}",
                orderChargedEvent.getName(), orderChargedEvent.getOrderId(), orderChargedEvent.getCustomerId());

        orderService.handleOrderCharged(orderChargedEvent);
    }

    @StreamListener(Channels.Events.ORDER_NOT_CHARGED)
    public void orderNotCharged(final OrderNotChargedEvent orderNotChargedEvent) {
        LOGGER.warn("Received a '{}' event for the order {} of the customer {}",
                orderNotChargedEvent.getName(), orderNotChargedEvent.getOrderId(), orderNotChargedEvent.getCustomerId());

        orderService.handleOrderNotCharged(orderNotChargedEvent);
    }

    @StreamListener(Channels.Events.ORDER_SHIPPED)
    public void orderShipped(final OrderShippedEvent orderShippedEvent) {
        LOGGER.debug("Received a '{}' event for the order {} of the customer {}",
                orderShippedEvent.getName(), orderShippedEvent.getOrderId(), orderShippedEvent.getCustomerId());

        orderService.handleOrderShipped(orderShippedEvent);
    }
}
