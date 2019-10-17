package net.safedata.microservices.training.order.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    @Output(Channels.Commands.SHIP_ORDER)
    MessageChannel shipOrder();

    @Output(Channels.Commands.CHARGE_ORDER)
    MessageChannel chargeOrder();

    @Output(Channels.Events.ORDER_CREATED)
    MessageChannel orderCreated();
}