package net.safedata.microservices.training.shipping.port;

import net.safedata.microservices.training.shipping.marker.port.OutboundPort;
import net.safedata.microservices.training.shipping.message.event.OrderShippedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishOrderShippedEvent(final OrderShippedEvent orderShippedEvent);
}
