package net.safedata.microservices.training.customer.outbound.adapter;

import net.safedata.microservices.training.customer.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.helper.MessagePublisher;
import net.safedata.microservices.training.message.Bindings;
import net.safedata.microservices.training.message.event.customer.CustomerCreatedEvent;
import net.safedata.microservices.training.message.event.customer.CustomerUpdatedEvent;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements MessagingOutboundPort, OutboundAdapter {

    private final MessagePublisher messagePublisher;

    @Autowired
    public MessageProducer(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void publishCustomerCreatedEvent(final CustomerCreatedEvent customerCreatedEvent) {
        messagePublisher.sendMessage(Bindings.CUSTOMER_CREATED, customerCreatedEvent);
    }

    @Override
    public void publishCustomerUpdatedEvent(final CustomerUpdatedEvent customerUpdatedEvent) {
        messagePublisher.sendMessage(Bindings.CUSTOMER_CREATED, customerUpdatedEvent);
    }
}
