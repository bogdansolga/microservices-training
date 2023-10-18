package net.safedata.microservices.training.customer.config;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.message.event.customer.CustomerCreatedEvent;
import net.safedata.microservices.training.message.event.customer.CustomerUpdatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PublishersConfig {

    @Bean
    public Function<CustomerCreatedEvent, Message<CustomerCreatedEvent>> customerCreatedProducer() {
        return MessageCreator::create;
    }

    @Bean
    public Function<CustomerUpdatedEvent, Message<CustomerUpdatedEvent>> customerUpdatedProducer() {
        return MessageCreator::create;
    }
}
