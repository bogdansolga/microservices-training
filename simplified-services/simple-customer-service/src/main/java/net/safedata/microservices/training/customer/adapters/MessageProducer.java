package net.safedata.microservices.training.customer.adapters;

import net.safedata.microservices.training.customer.channels.OutboundChannels;
import net.safedata.microservices.training.customer.messages.event.CustomerCreatedEvent;
import net.safedata.microservices.training.customer.messages.event.CustomerUpdatedEvent;
import net.safedata.microservices.training.customer.ports.MessagingOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(OutboundChannels.class)
public class MessageProducer implements MessagingOutboundPort {

    private final OutboundChannels outboundChannels;

    @Autowired
    public MessageProducer(final OutboundChannels outboundChannels) {
        this.outboundChannels = outboundChannels;
    }

    @Override
    public void publishCustomerCreatedEvent(CustomerCreatedEvent customerCreatedEvent) {
        outboundChannels.customerCreated()
                        .send(MessageCreator.create(customerCreatedEvent));
    }

    @Override
    public void publishCustomerUpdatedEvent(final CustomerUpdatedEvent customerUpdatedEvent) {
        outboundChannels.customerUpdated()
                        .send(MessageCreator.create(customerUpdatedEvent));
    }
}
