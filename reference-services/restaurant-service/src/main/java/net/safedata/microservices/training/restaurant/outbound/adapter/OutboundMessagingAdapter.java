package net.safedata.microservices.training.restaurant.outbound.adapter;

import net.safedata.microservices.training.helper.MessagePublisher;
import net.safedata.microservices.training.message.OutputBindings;
import net.safedata.microservices.training.message.command.order.ProcessOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;
import net.safedata.microservices.training.restaurant.outbound.port.OutboundMessagingPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutboundMessagingAdapter implements OutboundMessagingPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutboundMessagingAdapter.class);

    private final MessagePublisher messagePublisher;

    @Autowired
    public OutboundMessagingAdapter(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void publishOrderProcessedEvent(OrderProcessedEvent orderProcessedEvent) {
        messagePublisher.sendMessage(OutputBindings.ORDER_PROCESSED, orderProcessedEvent);
        LOGGER.info("The OrderProcessedEvent '{}' was published", orderProcessedEvent);
    }
}
