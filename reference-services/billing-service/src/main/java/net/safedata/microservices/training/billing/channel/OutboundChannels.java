package net.safedata.microservices.training.billing.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    @Output(Channels.Events.ORDER_CHARGED)
    MessageChannel orderCharged();

    @Output(Channels.Events.ORDER_NOT_CHARGED)
    MessageChannel orderNotCharged();
}