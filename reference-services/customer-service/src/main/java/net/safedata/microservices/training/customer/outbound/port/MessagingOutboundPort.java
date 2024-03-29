package net.safedata.microservices.training.customer.outbound.port;

import net.safedata.microservices.training.marker.port.OutboundPort;
import net.safedata.microservices.training.message.command.order.ProcessOrderCommand;
import net.safedata.microservices.training.message.event.customer.CustomerCreatedEvent;
import net.safedata.microservices.training.message.event.customer.CustomerUpdatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MessagingOutboundPort extends OutboundPort {
    void publishCustomerCreatedEvent(final CustomerCreatedEvent customerCreatedEvent);

    void publishCustomerUpdatedEvent(final CustomerUpdatedEvent customerUpdatedEvent);

    void publishProcessOrderCommand(ProcessOrderCommand processOrderCommand);
}
