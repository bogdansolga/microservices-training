package net.safedata.microservices.training.shipping.ports;

import net.safedata.microservices.training.shipping.marker.port.OutboundPort;
import net.safedata.microservices.training.shipping.messages.event.OrderShippedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishOrderShippedEvent(final OrderShippedEvent orderShippedEvent);
}
