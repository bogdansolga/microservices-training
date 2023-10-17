package net.safedata.microservices.training.order.outbound.producer;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("orderCreatedProducer")
public class OrderCreatedProducer implements Function<OrderCreatedEvent, Message<OrderCreatedEvent>> {

    @Override
    public Message<OrderCreatedEvent> apply(OrderCreatedEvent orderCreatedEvent) {
        return MessageCreator.create(orderCreatedEvent);
    }
}
