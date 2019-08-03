package net.safedata.microservices.training.billing.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    // the channel on which 'OrderCharged' events are sent
    String ORDER_CHARGED = "order_charged";

    // the channel on which 'OrderNotCharged' events are sent
    String ORDER_NOT_CHARGED = "order_not_charged";

    @Output(ORDER_CHARGED)
    MessageChannel orderCharged();

    @Output(ORDER_NOT_CHARGED)
    MessageChannel orderNotCharged();
}