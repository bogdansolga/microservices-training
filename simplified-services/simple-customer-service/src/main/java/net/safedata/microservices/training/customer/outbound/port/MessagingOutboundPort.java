package net.safedata.microservices.training.customer.outbound.port;

import net.safedata.microservices.training.customer.marker.port.OutboundPort;
import net.safedata.microservices.training.customer.message.event.CustomerCreatedEvent;
import net.safedata.microservices.training.customer.message.event.CustomerUpdatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishCustomerCreatedEvent(final CustomerCreatedEvent customerCreatedEvent);

    void publishCustomerUpdatedEvent(final CustomerUpdatedEvent customerUpdatedEvent);
}
