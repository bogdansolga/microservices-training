package net.safedata.microservices.training.restaurant.service;

import net.safedata.microservices.training.message.command.order.DeliverOrderCommand;
import net.safedata.microservices.training.message.command.order.ProcessOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;
import net.safedata.microservices.training.restaurant.inbound.port.MessagingInboundPort;
import net.safedata.microservices.training.restaurant.outbound.port.MessagingOutboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService implements MessagingInboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);

    private final MessagingOutboundPort messagingOutboundPort;

    @Autowired
    public RestaurantService(MessagingOutboundPort messagingOutboundPort) {
        this.messagingOutboundPort = messagingOutboundPort;
    }

    @Override
    public void processOrder(ProcessOrderCommand processOrderCommand) {
        LOGGER.info("Processing an order for the customer with the ID {}...", processOrderCommand.getCustomerId());

        //TODO insert processing magic here

        DeliverOrderCommand deliverOrderCommand = createDeliverOrderCommand(processOrderCommand);
        messagingOutboundPort.publishDeliverOrderCommand(deliverOrderCommand);

        messagingOutboundPort.publishOrderProcessedEvent(
                new OrderProcessedEvent(processOrderCommand.getMessageId(), processOrderCommand.getCustomerId(),
                        processOrderCommand.getCustomerId(), -123L));
    }

    private DeliverOrderCommand createDeliverOrderCommand(ProcessOrderCommand processOrderCommand) {
        return new DeliverOrderCommand(processOrderCommand.getCustomerId(),
                processOrderCommand.getMessageId(), processOrderCommand.getProductName(),
                processOrderCommand.getOrderTotal());
    }
}
