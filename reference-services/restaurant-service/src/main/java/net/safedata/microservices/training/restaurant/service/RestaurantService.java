package net.safedata.microservices.training.restaurant.service;

import net.safedata.microservices.training.message.command.order.ProcessOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;
import net.safedata.microservices.training.restaurant.inbound.port.InboundMessagingPort;
import net.safedata.microservices.training.restaurant.outbound.port.OutboundMessagingPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService implements InboundMessagingPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);

    private final OutboundMessagingPort outboundMessagingPort;

    @Autowired
    public RestaurantService(OutboundMessagingPort outboundMessagingPort) {
        this.outboundMessagingPort = outboundMessagingPort;
    }

    @Override
    public void processOrder(ProcessOrderCommand processOrderCommand) {
        LOGGER.info("Processing an order for the customer with the ID {}...", processOrderCommand.getCustomerId());

        //TODO insert processing magic here

        outboundMessagingPort.publishOrderProcessedEvent(
                new OrderProcessedEvent(processOrderCommand.getMessageId(), processOrderCommand.getCustomerId(),
                        processOrderCommand.getCustomerId(), -123L));
    }
}
