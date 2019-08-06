package net.safedata.microservices.training.order.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    @Input(Channels.Inbound.CREATE_ORDER)
    MessageChannel createOrder();

    @Input(Channels.Inbound.CUSTOMER_UPDATED)
    MessageChannel customerUpdated();

    @Input(Channels.Inbound.ORDER_CHARGED)
    MessageChannel orderCharged();

    @Input(Channels.Inbound.ORDER_NOT_CHARGED)
    MessageChannel orderNotCharged();

    @Input(Channels.Inbound.ORDER_SHIPPED)
    MessageChannel orderShipped();
}