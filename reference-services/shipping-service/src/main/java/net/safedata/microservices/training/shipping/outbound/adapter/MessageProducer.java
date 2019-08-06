package net.safedata.microservices.training.shipping.outbound.adapter;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.marker.port.OutboundPort;
import net.safedata.microservices.training.message.event.order.OrderShippedEvent;
import net.safedata.microservices.training.shipping.channel.OutboundChannels;
import net.safedata.microservices.training.shipping.outbound.port.MessagingOutboundPort;
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
