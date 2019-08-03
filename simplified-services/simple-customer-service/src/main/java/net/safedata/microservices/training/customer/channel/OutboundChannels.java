package net.safedata.microservices.training.customer.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    // the channel on which 'CustomerCreated' events are sent
    String CUSTOMER_CREATED = "customer_created";

    // the channel on which 'CustomerUpdated' events are sent
    String CUSTOMER_UPDATED = "customer_updated";

    @Output(CUSTOMER_CREATED)
    MessageChannel customerCreated();

    @Output(CUSTOMER_UPDATED)
    MessageChannel customerUpdated();
}