package net.safedata.microservices.training.shipping.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    // the channel on which 'ShipOrder' commands are sent
    String SHIP_ORDER = "ship_order";

    @Input(SHIP_ORDER)
    MessageChannel shipOrder();
}