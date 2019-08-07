package net.safedata.microservices.training.order.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    @Output(Channels.SHIP_ORDER)
    MessageChannel shipOrder();

    @Output(Channels.CHARGE_ORDER)
    MessageChannel chargeOrder();

    @Output(Channels.ORDER_CREATED)
    MessageChannel orderCreated();
}