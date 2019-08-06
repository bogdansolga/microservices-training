package net.safedata.microservices.training.shipping.outbound.port;

import net.safedata.microservices.training.marker.port.OutboundPort;
import net.safedata.microservices.training.message.event.order.OrderShippedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishOrderShippedEvent(final OrderShippedEvent orderShippedEvent);
}
