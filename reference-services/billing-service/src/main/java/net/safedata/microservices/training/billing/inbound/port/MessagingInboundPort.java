package net.safedata.microservices.training.billing.inbound.port;

import net.safedata.microservices.training.marker.port.InboundPort;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;

public interface MessagingInboundPort extends InboundPort {

    void chargeOrder(ChargeOrderCommand chargeOrderCommand);
}
