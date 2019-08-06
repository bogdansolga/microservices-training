package net.safedata.microservices.training.order.outbound.adapter;

import net.safedata.microservices.training.order.channel.OutboundChannels;
import net.safedata.microservices.training.order.marker.adapter.OutboundAdapter;
import net.safedata.microservices.training.order.message.event.OrderCreatedEvent;
import net.safedata.microservices.training.order.outbound.port.MessagingOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(OutboundChannels.class)
public class MessageProducer implements MessagingOutboundPort, OutboundAdapter {

    private final OutboundChannels outboundChannels;

    @Autowired
    public MessageProducer(final OutboundChannels outboundChannels) {
        this.outboundChannels = outboundChannels;
    }

    public void publishOrderCreatedEvent(final OrderCreatedEvent orderCreatedEvent) {
        outboundChannels.orderCreated()
                        .send(MessageCreator.create(orderCreatedEvent));
    }
}
