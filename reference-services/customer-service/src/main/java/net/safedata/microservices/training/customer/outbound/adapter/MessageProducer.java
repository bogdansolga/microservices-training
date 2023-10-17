package net.safedata.microservices.training.customer.outbound.adapter;

import net.safedata.microservices.training.customer.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.message.event.customer.CustomerCreatedEvent;
import net.safedata.microservices.training.message.event.customer.CustomerUpdatedEvent;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements MessagingOutboundPort, OutboundAdapter {

    @Override
    public void publishCustomerCreatedEvent(final CustomerCreatedEvent customerCreatedEvent) {
    }

    @Override
    public void publishCustomerUpdatedEvent(final CustomerUpdatedEvent customerUpdatedEvent) {
    }
}
