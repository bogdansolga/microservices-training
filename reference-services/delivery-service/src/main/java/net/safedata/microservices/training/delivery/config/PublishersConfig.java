package net.safedata.microservices.training.delivery.config;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.message.event.order.OrderDeliveredEvent;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PublishersConfig {

    @Bean
    public Function<OrderDeliveredEvent, Message<OrderDeliveredEvent>> orderDelivered() {
        return MessageCreator::create;
    }
}
