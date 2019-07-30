package net.safedata.microservices.training.customer.adapters;

import net.safedata.microservices.training.customer.channels.OutboundChannels;
import net.safedata.microservices.training.customer.events.CustomerCreatedEvent;
import net.safedata.microservices.training.customer.ports.MessagingOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
@EnableBinding(OutboundChannels.class)
public class MessageProducer implements MessagingOutboundPort {

    private final OutboundChannels outboundChannels;

    @Autowired
    public MessageProducer(final OutboundChannels outboundChannels) {
        this.outboundChannels = outboundChannels;
    }

    public void publishEvent(final CustomerCreatedEvent customerCreatedEvent) {
        outboundChannels.customer()
                        .send(createMessage(customerCreatedEvent));
    }

    private Message<?> createMessage(final CustomerCreatedEvent customerCreatedEvent) {
        return MessageBuilder.withPayload(customerCreatedEvent)
                             .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                             .build();
    }
}
