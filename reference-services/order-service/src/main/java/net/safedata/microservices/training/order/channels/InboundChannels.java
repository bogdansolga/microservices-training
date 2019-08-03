package net.safedata.microservices.training.order.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    // the channel on which 'CreateOrder' commands are received
    String CREATE_ORDER = "create_order";

    // the channel on which 'CustomerUpdated' events are received
    String CUSTOMER_UPDATED = "customer_updated";

    // the channel on which 'OrderCharged' events are received
    String ORDER_CHARGED = "order_charged";

    // the channel on which 'OrderNotCharged' events are received
    String ORDER_NOT_CHARGED = "order_not_charged";

    // the channel on which 'OrderShipped' events are received
    String ORDER_SHIPPED = "order_shipped";

    @Input(CREATE_ORDER)
    MessageChannel createOrder();

    @Input(CUSTOMER_UPDATED)
    MessageChannel customerUpdated();

    @Input(ORDER_CHARGED)
    MessageChannel orderCharged();

    @Input(ORDER_NOT_CHARGED)
    MessageChannel orderNotCharged();

    @Input(ORDER_SHIPPED)
    MessageChannel orderShipped();
}