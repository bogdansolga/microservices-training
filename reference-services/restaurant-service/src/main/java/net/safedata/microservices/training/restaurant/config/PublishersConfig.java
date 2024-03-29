package net.safedata.microservices.training.restaurant.config;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.message.command.order.DeliverOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PublishersConfig {

    @Bean
    public Function<OrderProcessedEvent, Message<OrderProcessedEvent>> orderProcessed() {
        return MessageCreator::create;
    }

    @Bean
    public Function<DeliverOrderCommand, Message<DeliverOrderCommand>> deliverOrder() {
        return MessageCreator::create;
    }
}
