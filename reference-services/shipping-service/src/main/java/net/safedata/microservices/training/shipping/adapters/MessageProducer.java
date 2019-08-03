package net.safedata.microservices.training.shipping.adapters;

import net.safedata.microservices.training.shipping.channels.OutboundChannels;
import net.safedata.microservices.training.shipping.marker.port.OutboundPort;
import net.safedata.microservices.training.shipping.messages.event.OrderShippedEvent;
import net.safedata.microservices.training.shipping.ports.MessagingOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(OutboundChannels.class)
public class MessageProducer implements MessagingOutboundPort, OutboundPort {

    private final OutboundChannels outboundChannels;

    @Autowired
    public MessageProducer(final OutboundChannels outboundChannels) {
        this.outboundChannels = outboundChannels;
    }

    @Override
    public void publishOrderShippedEvent(final OrderShippedEvent orderShippedEvent) {
        outboundChannels.orderShipped()
                        .send(MessageCreator.create(orderShippedEvent));
    }
}
