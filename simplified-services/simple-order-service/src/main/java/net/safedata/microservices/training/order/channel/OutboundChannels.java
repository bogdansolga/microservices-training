package net.safedata.microservices.training.order.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    // the net.safedata.microservices.training.product.channel on which 'OrderCreated' events are sent
    String ORDER_CREATED = "order_created";

    @Output(ORDER_CREATED)
    MessageChannel orderCreated();
}