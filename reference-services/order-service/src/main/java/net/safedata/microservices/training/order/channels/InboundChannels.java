package net.safedata.microservices.training.order.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    // the channel on which 'CreateOrder' commands are received
    String ORDER_CREATE = "order_create";

    // the channel on which 'CustomerUpdated' events are received
    String CUSTOMER_UPDATED = "customer_updated";

    @Input(ORDER_CREATE)
    MessageChannel createOrder();

    @Input(CUSTOMER_UPDATED)
    MessageChannel customerUpdated();
}