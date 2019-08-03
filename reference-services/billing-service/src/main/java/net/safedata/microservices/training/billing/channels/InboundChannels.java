package net.safedata.microservices.training.billing.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    // the channel on which 'ChargeOrder' events are received
    String CHARGE_ORDER = "charge_order";

    @Input(CHARGE_ORDER)
    MessageChannel chargeOrder();
}