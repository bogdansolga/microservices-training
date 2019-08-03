package net.safedata.microservices.training.shipping.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    // the channel on which 'OrderShipped' events are sent
    String ORDER_SHIPPED = "order_shipped";

    @Output(ORDER_SHIPPED)
    MessageChannel orderShipped();
}