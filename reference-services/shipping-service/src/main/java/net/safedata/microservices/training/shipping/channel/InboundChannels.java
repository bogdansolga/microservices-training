package net.safedata.microservices.training.shipping.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    @Input(Channels.Commands.SHIP_ORDER)
    MessageChannel shipOrder();
}