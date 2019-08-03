package net.safedata.microservices.training.customer.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    // the channel on which 'OrderCreated' events are received
    String ORDER_CREATED = "order_created";

    // the channel on which 'OrderCharged' events are received
    String ORDER_CHARGED = "order_charged";

    // the channel on which 'OrderNotCharged' events are received
    String ORDER_NOT_CHARGED = "order_not_charged";

    // the channel on which 'OrderShipped' events are received
    String ORDER_SHIPPED = "order_shipped";

    @Input(ORDER_CREATED)
    MessageChannel orderCreated();

    @Input(ORDER_CHARGED)
    MessageChannel orderCharged();

    @Input(ORDER_NOT_CHARGED)
    MessageChannel orderNotCharged();

    @Input(ORDER_SHIPPED)
    MessageChannel orderShipped();
}