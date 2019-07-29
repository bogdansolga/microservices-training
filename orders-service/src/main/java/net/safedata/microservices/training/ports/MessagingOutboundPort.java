package net.safedata.microservices.training.ports;

import net.safedata.microservices.training.events.OrderCreatedEvent;
import net.safedata.microservices.training.marker.OutboundPort;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishEvent(final OrderCreatedEvent orderCreatedEvent);
}
