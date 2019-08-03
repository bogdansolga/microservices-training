package net.safedata.microservices.training.order.ports;

import net.safedata.microservices.training.order.marker.port.OutboundPort;
import net.safedata.microservices.training.order.message.command.ChargeOrderCommand;
import net.safedata.microservices.training.order.message.command.ShipOrderCommand;
import net.safedata.microservices.training.order.message.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishOrderCreatedEvent(final OrderCreatedEvent orderCreatedEvent);

    void publishChargeOrderCommand(final ChargeOrderCommand chargeOrderCommand);

    void publishShipOrderCommand(final ShipOrderCommand shipOrderCommand);
}
