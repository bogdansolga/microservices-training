package net.safedata.microservices.training.customer.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    // the channel on which 'OrderUpdated' events are received
    String ORDER_CREATED = "order_created";

    @Input(ORDER_CREATED)
    MessageChannel orderCreated();
}