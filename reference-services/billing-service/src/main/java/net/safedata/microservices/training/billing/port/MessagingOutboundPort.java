package net.safedata.microservices.training.billing.port;

import net.safedata.microservices.training.billing.marker.port.OutboundPort;
import net.safedata.microservices.training.billing.message.event.OrderChargedEvent;
import net.safedata.microservices.training.billing.message.event.OrderNotChargedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishOrderChargedEvent(final OrderChargedEvent orderChargedEvent);

    void publishOrderNotChargedEvent(final OrderNotChargedEvent orderNotChargedEvent);
}
