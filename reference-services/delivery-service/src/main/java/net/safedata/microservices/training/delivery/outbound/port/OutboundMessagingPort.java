package net.safedata.microservices.training.delivery.outbound.port;

import net.safedata.microservices.training.message.event.order.OrderDeliveredEvent;

public interface OutboundMessagingPort {
    public void publishOrderDeliveredEvent(OrderDeliveredEvent orderDeliveredEvent);
}
