package net.safedata.microservices.training.order.ports;

import net.safedata.microservices.training.order.marker.OutboundPort;
import net.safedata.microservices.training.order.message.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishEvent(final OrderCreatedEvent orderCreatedEvent);
}
