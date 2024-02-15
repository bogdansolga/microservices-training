package net.safedata.microservices.training.delivery.service;

import net.safedata.microservices.training.delivery.inbound.port.InboundMessagingPort;
import net.safedata.microservices.training.delivery.outbound.port.OutboundMessagingPort;
import net.safedata.microservices.training.message.command.order.DeliverOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderDeliveredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService implements InboundMessagingPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryService.class);

    private final OutboundMessagingPort outboundMessagingPort;

    @Autowired
    public DeliveryService(OutboundMessagingPort outboundMessagingPort) {
        this.outboundMessagingPort = outboundMessagingPort;
    }

    @Override
    public void deliverOrder(DeliverOrderCommand deliverOrderCommand) {
        LOGGER.info("Delivering an order for the customer with the ID {}...",
                deliverOrderCommand.getCustomerId());

        //TODO insert courier magic in here

        OrderDeliveredEvent orderDeliveredEvent = createOrderDeliveredEvent(deliverOrderCommand);

        outboundMessagingPort.publishOrderDeliveredEvent(orderDeliveredEvent);
    }

    private OrderDeliveredEvent createOrderDeliveredEvent(DeliverOrderCommand deliverOrderCommand) {
        return new OrderDeliveredEvent(deliverOrderCommand.getMessageId(),
                120L, deliverOrderCommand.getCustomerId(), 250L);
    }
}
