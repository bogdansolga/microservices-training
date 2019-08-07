package net.safedata.microservices.training.customer.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    @Input(Channels.ORDER_CREATED)
    MessageChannel orderCreated();

    @Input(Channels.ORDER_CHARGED)
    MessageChannel orderCharged();

    @Input(Channels.ORDER_NOT_CHARGED)
    MessageChannel orderNotCharged();

    @Input(Channels.ORDER_SHIPPED)
    MessageChannel orderShipped();
}