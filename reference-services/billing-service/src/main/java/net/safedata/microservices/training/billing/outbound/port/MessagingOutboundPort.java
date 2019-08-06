package net.safedata.microservices.training.billing.outbound.port;

import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.marker.port.OutboundPort;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishOrderChargedEvent(final OrderChargedEvent orderChargedEvent);

    void publishOrderNotChargedEvent(final OrderNotChargedEvent orderNotChargedEvent);
}
