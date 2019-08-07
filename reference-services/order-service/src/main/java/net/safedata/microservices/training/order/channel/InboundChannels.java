package net.safedata.microservices.training.order.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    @Input(Channels.CREATE_ORDER)
    MessageChannel createOrder();

    @Input(Channels.CUSTOMER_UPDATED)
    MessageChannel customerUpdated();

    @Input(Channels.ORDER_CHARGED)
    MessageChannel orderCharged();

    @Input(Channels.ORDER_NOT_CHARGED)
    MessageChannel orderNotCharged();

    @Input(Channels.ORDER_SHIPPED)
    MessageChannel orderShipped();
}