package net.safedata.microservices.training.order.channels;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    String ORDER_CREATED = "order_created";

    @Output(ORDER_CREATED)
    MessageChannel orderCreated();
}