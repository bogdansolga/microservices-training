package net.safedata.microservices.training.shipping.config;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.message.event.order.OrderShippedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PublishersConfig {

    @Bean
    public Function<OrderShippedEvent, Message<OrderShippedEvent>> orderShippedProducer() {
        return MessageCreator::create;
    }
}
