package net.safedata.microservices.training.billing.adapter;

import net.safedata.microservices.training.billing.channel.OutboundChannels;
import net.safedata.microservices.training.billing.marker.adapter.OutboundAdapter;
import net.safedata.microservices.training.billing.message.event.OrderChargedEvent;
import net.safedata.microservices.training.billing.message.event.OrderNotChargedEvent;
import net.safedata.microservices.training.billing.port.MessagingOutboundPort;
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

    @Override
    public void publishOrderChargedEvent(final OrderChargedEvent orderChargedEvent) {
        outboundChannels.orderCharged()
                        .send(MessageCreator.create(orderChargedEvent));
    }

    @Override
    public void publishOrderNotChargedEvent(final OrderNotChargedEvent orderNotChargedEvent) {
        outboundChannels.orderCharged()
                        .send(MessageCreator.create(orderNotChargedEvent));
    }
}
