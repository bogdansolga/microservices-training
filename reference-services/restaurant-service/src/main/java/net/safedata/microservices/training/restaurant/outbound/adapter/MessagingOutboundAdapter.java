package net.safedata.microservices.training.restaurant.outbound.adapter;

import net.safedata.microservices.training.helper.MessagePublisher;
import net.safedata.microservices.training.message.OutputBindings;
import net.safedata.microservices.training.message.command.order.DeliverOrderCommand;
import net.safedata.microservices.training.message.command.order.ProcessOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import net.safedata.microservices.training.restaurant.outbound.port.MessagingOutboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagingOutboundAdapter implements MessagingOutboundPort, OutboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingOutboundAdapter.class);

    private final MessagePublisher messagePublisher;

    @Autowired
    public MessagingOutboundAdapter(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void publishOrderProcessedEvent(OrderProcessedEvent orderProcessedEvent) {
        messagePublisher.sendMessage(OutputBindings.ORDER_PROCESSED, orderProcessedEvent);
        LOGGER.info("The OrderProcessedEvent '{}' was published", orderProcessedEvent);
    }

    @Override
    public void publishDeliverOrderCommand(DeliverOrderCommand deliverOrderCommand) {
        messagePublisher.sendMessage(OutputBindings.DELIVER_ORDER, deliverOrderCommand);
        LOGGER.info("The DeliverOrderCommand '{}' was published", deliverOrderCommand);
    }
}
