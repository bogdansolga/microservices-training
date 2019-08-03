package net.safedata.microservices.training.order.ports;

import net.safedata.microservices.training.order.marker.port.Port;
import net.safedata.microservices.training.order.message.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends Port {
    void publishOrderCreatedEvent(final OrderCreatedEvent orderCreatedEvent);
}
