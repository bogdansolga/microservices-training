package net.safedata.microservices.training.billing.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    @Input(Channels.CHARGE_ORDER)
    MessageChannel chargeOrder();
}