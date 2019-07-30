package net.safedata.microservices.training.customer.channels;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {
    String CUSTOMER = "customer_out";

    @Output(CUSTOMER)
    MessageChannel customer();
}