package net.safedata.microservices.training.order.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    String ORDERS = "orders_in";

    @Input(ORDERS)
    MessageChannel orders();
}