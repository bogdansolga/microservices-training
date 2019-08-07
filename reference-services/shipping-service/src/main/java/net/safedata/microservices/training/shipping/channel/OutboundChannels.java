package net.safedata.microservices.training.shipping.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    @Output(Channels.ORDER_SHIPPED)
    MessageChannel orderShipped();
}