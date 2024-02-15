package net.safedata.microservices.training.delivery.outbound.adapter;

import net.safedata.microservices.training.delivery.outbound.port.OutboundMessagingPort;
import net.safedata.microservices.training.helper.MessagePublisher;
import net.safedata.microservices.training.message.OutputBindings;
import net.safedata.microservices.training.message.event.order.OrderDeliveredEvent;
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
    public void publishOrderDeliveredEvent(OrderDeliveredEvent orderDeliveredEvent) {
        LOGGER.info("Publishing an OrderDeliveredEvent for the customer with the ID {}...",
                orderDeliveredEvent.getCustomerId());
        messagePublisher.sendMessage(OutputBindings.ORDER_DELIVERED, orderDeliveredEvent);
    }
}
