package net.safedata.microservices.training.customer.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {
    String CUSTOMER = "customer_in";

    @Input(CUSTOMER)
    MessageChannel customer();
}