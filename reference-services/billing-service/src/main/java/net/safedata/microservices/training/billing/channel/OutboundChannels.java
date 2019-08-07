package net.safedata.microservices.training.billing.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    @Output(Channels.ORDER_CHARGED)
    MessageChannel orderCharged();

    @Output(Channels.ORDER_NOT_CHARGED)
    MessageChannel orderNotCharged();
}