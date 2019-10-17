package net.safedata.microservices.training.customer.channel;

import net.safedata.microservices.training.marker.message.Channels;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    @Output(Channels.Events.CUSTOMER_CREATED)
    MessageChannel customerCreated();

    @Output(Channels.Events.CUSTOMER_UPDATED)
    MessageChannel customerUpdated();
}