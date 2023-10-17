package net.safedata.microservices.training.order.config;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Function;

@Configuration
public class ProducersConfig {

    @Bean
    public Function<OrderCreatedEvent, Message<OrderCreatedEvent>> orderCreatedProducer() {
        return MessageCreator::create;
    }

    @Bean
    public Function<ChargeOrderCommand, Message<ChargeOrderCommand>> chargeOrderProducer() {
        return MessageCreator::create;
    }
}
