package net.safedata.microservices.training.customer.outbound.adapter;

import net.safedata.microservices.training.customer.channel.OutboundChannels;
import net.safedata.microservices.training.message.event.customer.CustomerCreatedEvent;
import net.safedata.microservices.training.message.event.customer.CustomerUpdatedEvent;
import net.safedata.microservices.training.customer.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(OutboundChannels.class)
public class MessageProducer implements MessagingOutboundPort, OutboundAdapter {

    private final OutboundChannels outboundChannels;

    @Autowired
    public MessageProducer(final OutboundChannels outboundChannels) {
        this.outboundChannels = outboundChannels;
    }

    @Override
    public void publishCustomerCreatedEvent(final CustomerCreatedEvent customerCreatedEvent) {
        outboundChannels.customerCreated()
                        .send(MessageCreator.create(customerCreatedEvent));
    }

    @Override
    public void publishCustomerUpdatedEvent(final CustomerUpdatedEvent customerUpdatedEvent) {
        outboundChannels.customerUpdated()
                        .send(MessageCreator.create(customerUpdatedEvent));
    }
}
