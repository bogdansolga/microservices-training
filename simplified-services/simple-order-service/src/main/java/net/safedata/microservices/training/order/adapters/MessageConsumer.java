package net.safedata.microservices.training.order.adapters;

import net.safedata.microservices.training.order.channels.InboundChannels;
import net.safedata.microservices.training.order.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.order.message.command.CreateOrderCommand;
import net.safedata.microservices.training.order.message.event.CustomerUpdatedEvent;
import net.safedata.microservices.training.order.service.OrderService;
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

    @StreamListener(InboundChannels.CREATE_ORDER)
    public void createOrder(final CreateOrderCommand createOrderCommand) {
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        LOGGER.debug("Received a '{}' command, the ordered item is '{}', the customer ID is {}",
                createOrderCommand.getName(), createOrderCommand.getProductName(), createOrderCommand.getCustomerId());

        orderService.createOrder(createOrderCommand);
    }

    @StreamListener(InboundChannels.CUSTOMER_UPDATED)
    public void customerUpdated(final CustomerUpdatedEvent customerUpdatedEvent) {
        LOGGER.debug("Received a '{}' event, the ID of the updated customer is {}",
                customerUpdatedEvent.getName(), customerUpdatedEvent.getCustomerId());

        orderService.handleCustomerUpdated(customerUpdatedEvent);
    }
}
