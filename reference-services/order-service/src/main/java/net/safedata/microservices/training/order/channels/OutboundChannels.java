package net.safedata.microservices.training.order.channels;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    // the channel on which 'ShipOrder' commands are sent
    String SHIP_ORDER = "ship_order";

    // the channel on which 'ChargeOrder' commands are sent
    String CHARGE_ORDER = "charge_order";

    // the channel on which 'OrderCreated' events are sent
    String ORDER_CREATED = "order_created";

    @Output(SHIP_ORDER)
    MessageChannel shipOrder();

    @Output(CHARGE_ORDER)
    MessageChannel chargeOrder();

    @Output(ORDER_CREATED)
    MessageChannel orderCreated();
}