package net.safedata.microservices.training.order.config;

import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ProducersConfig {

    @Bean
    public Function<OrderCreatedEvent, OrderCreatedEvent> orderCreatedProducer() {
        return Function.identity();
    }

    @Bean
    public Function<ChargeOrderCommand, ChargeOrderCommand> chargeOrderProducer() {
        return Function.identity();
    }
}
