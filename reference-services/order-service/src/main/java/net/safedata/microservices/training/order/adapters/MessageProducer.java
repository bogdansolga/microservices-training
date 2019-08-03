package net.safedata.microservices.training.order.adapters;

import net.safedata.microservices.training.order.channels.OutboundChannels;
import net.safedata.microservices.training.order.marker.OutboundAdapter;
import net.safedata.microservices.training.order.message.OrderCreatedEvent;
import net.safedata.microservices.training.order.ports.MessagingOutboundPort;
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

    public void publishEvent(final OrderCreatedEvent orderCreatedEvent) {
        outboundChannels.orderCreated()
                        .send(MessageCreator.create(orderCreatedEvent));
    }
}
