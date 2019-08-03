package net.safedata.microservices.training.customer.ports;

import net.safedata.microservices.training.customer.events.CustomerCreatedEvent;
import net.safedata.microservices.training.customer.events.CustomerUpdatedEvent;
import net.safedata.microservices.training.customer.marker.OutboundPort;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishCustomerCreatedEvent(final CustomerCreatedEvent customerCreatedEvent);

    void publishCustomerUpdatedEvent(final CustomerUpdatedEvent customerUpdatedEvent);
}
