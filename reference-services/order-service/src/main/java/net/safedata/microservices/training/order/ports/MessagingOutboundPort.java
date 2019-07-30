package net.safedata.microservices.training.order.ports;

import net.safedata.microservices.training.order.events.OrderCreatedEvent;
import net.safedata.microservices.training.order.marker.OutboundPort;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishEvent(final OrderCreatedEvent orderCreatedEvent);
}
