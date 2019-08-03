package net.safedata.microservices.training.shipping.adapter;

import net.safedata.microservices.training.shipping.channel.OutboundChannels;
import net.safedata.microservices.training.shipping.marker.port.OutboundPort;
import net.safedata.microservices.training.shipping.message.event.OrderShippedEvent;
import net.safedata.microservices.training.shipping.port.MessagingOutboundPort;
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
