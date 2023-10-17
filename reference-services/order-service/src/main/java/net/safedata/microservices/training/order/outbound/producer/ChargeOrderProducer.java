package net.safedata.microservices.training.order.outbound.producer;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component("chargeOrderProducer")
public class ChargeOrderProducer implements Function<ChargeOrderCommand, Message<ChargeOrderCommand>> {

    @Override
    public Message<ChargeOrderCommand> apply(ChargeOrderCommand chargeOrderCommand) {
        return MessageCreator.create(chargeOrderCommand);
    }
}
