package net.safedata.microservices.training.billing.outbound.adapter;

import net.safedata.microservices.training.helper.MessagePublisher;
import net.safedata.microservices.training.message.Bindings;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.billing.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements MessagingOutboundPort, OutboundAdapter {

    private final MessagePublisher messagePublisher;

    @Autowired
    public MessageProducer(final MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void publishOrderChargedEvent(final OrderChargedEvent orderChargedEvent) {
        messagePublisher.sendMessage(Bindings.ORDER_CHARGED, orderChargedEvent);
    }

    @Override
    public void publishOrderNotChargedEvent(final OrderNotChargedEvent orderNotChargedEvent) {
    }
}
