package net.safedata.microservices.training.order.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    @Input(Channels.Commands.CREATE_ORDER)
    MessageChannel createOrder();

    @Input(Channels.Events.CUSTOMER_UPDATED)
    MessageChannel customerUpdated();

    @Input(Channels.Events.ORDER_CHARGED)
    MessageChannel orderCharged();

    @Input(Channels.Events.ORDER_NOT_CHARGED)
    MessageChannel orderNotCharged();

    @Input(Channels.Events.ORDER_SHIPPED)
    MessageChannel orderShipped();
}