package net.safedata.microservices.training.billing.config;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Function;

@Configuration
public class ProducersConfig {

    @Bean
    public Function<OrderChargedEvent, Message<OrderChargedEvent>> orderChargedProducer() {
        return MessageCreator::create;
    }

    @Bean
    public Function<OrderNotChargedEvent, Message<OrderNotChargedEvent>> orderNotChargedProducer() {
        return MessageCreator::create;
    }
}
