package net.safedata.microservices.training.shipping.outbound.adapter;

import net.safedata.microservices.training.helper.MessagePublisher;
import net.safedata.microservices.training.marker.port.OutboundPort;
import net.safedata.microservices.training.message.OutputBindings;
import net.safedata.microservices.training.message.event.order.OrderShippedEvent;
import net.safedata.microservices.training.shipping.outbound.port.MessagingOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements MessagingOutboundPort, OutboundPort {

    private final MessagePublisher messagePublisher;

    @Autowired
    public MessageProducer(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void publishOrderShippedEvent(final OrderShippedEvent orderShippedEvent) {
        messagePublisher.sendMessage(OutputBindings.ORDER_SHIPPED, orderShippedEvent);
    }
}
