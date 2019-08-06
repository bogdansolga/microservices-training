package net.safedata.microservices.training.billing.outbound.adapter;

import net.safedata.microservices.training.billing.channel.OutboundChannels;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.billing.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
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
