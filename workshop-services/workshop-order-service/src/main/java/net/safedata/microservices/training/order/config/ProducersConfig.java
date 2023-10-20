package net.safedata.microservices.training.order.config;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import net.safedata.microservices.training.message.command.order.UpdateOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import net.safedata.microservices.training.message.event.order.OrderUpdatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Function;

@Configuration
public class ProducersConfig {

    @Bean
    public Function<OrderUpdatedEvent, Message<OrderUpdatedEvent>> orderUpdatedProducer() {
        return MessageCreator::create;
    }

    @Bean
    public Function<UpdateOrderCommand, Message<UpdateOrderCommand>> updateOrderProducer() {
        return MessageCreator::create;
    }
}
