package net.safedata.microservices.training.billing.outbound.producer;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("orderChargedProducer")
public class OrderChargedProducer implements Function<OrderChargedEvent, Message<OrderChargedEvent>> {

    @Override
    public Message<OrderChargedEvent> apply(OrderChargedEvent orderChargedEvent) {
        return MessageCreator.create(orderChargedEvent);
    }
}
