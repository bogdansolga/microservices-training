package net.safedata.microservices.training.order.channels;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    String ORDERS = "orders_out";

    @Output(ORDERS)
    MessageChannel orders();
}