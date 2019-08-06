package net.safedata.microservices.training.order.outbound.port;

import net.safedata.microservices.training.marker.port.OutboundPort;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import net.safedata.microservices.training.message.command.order.ShipOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishOrderCreatedEvent(final OrderCreatedEvent orderCreatedEvent);

    void publishChargeOrderCommand(final ChargeOrderCommand chargeOrderCommand);

    void publishShipOrderCommand(final ShipOrderCommand shipOrderCommand);
}
