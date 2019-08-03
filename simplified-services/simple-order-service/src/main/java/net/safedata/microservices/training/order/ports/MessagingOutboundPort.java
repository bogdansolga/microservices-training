package net.safedata.microservices.training.order.ports;

import net.safedata.microservices.training.order.marker.port.OutboundPort;
import net.safedata.microservices.training.order.message.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishOrderCreatedEvent(final OrderCreatedEvent orderCreatedEvent);
}
