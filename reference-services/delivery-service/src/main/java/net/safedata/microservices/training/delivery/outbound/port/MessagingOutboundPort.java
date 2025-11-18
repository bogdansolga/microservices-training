package net.safedata.microservices.training.delivery.outbound.port;

import net.safedata.microservices.training.marker.port.OutboundPort;
import net.safedata.microservices.training.message.event.order.OrderDeliveredEvent;

public interface MessagingOutboundPort extends OutboundPort {
    public void publishOrderDeliveredEvent(OrderDeliveredEvent orderDeliveredEvent);
}
