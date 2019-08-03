package net.safedata.microservices.training.customer.ports;

import net.safedata.microservices.training.customer.marker.port.OutboundPort;
import net.safedata.microservices.training.customer.messages.event.CustomerCreatedEvent;
import net.safedata.microservices.training.customer.messages.event.CustomerUpdatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishCustomerCreatedEvent(final CustomerCreatedEvent customerCreatedEvent);

    void publishCustomerUpdatedEvent(final CustomerUpdatedEvent customerUpdatedEvent);
}
