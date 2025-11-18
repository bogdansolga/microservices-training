package net.safedata.microservices.training.order.outbound.adapter;

import net.safedata.microservices.training.helper.MessagePublisher;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import net.safedata.microservices.training.message.OutputBindings;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import net.safedata.microservices.training.order.outbound.port.MessagingOutboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagingOutboundAdapter implements MessagingOutboundPort, OutboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingOutboundAdapter.class);

    private final MessagePublisher messagePublisher;

    @Autowired
    public MessagingOutboundAdapter(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void publishOrderCreatedEvent(final OrderCreatedEvent orderCreatedEvent) {
        //TODO find a way to directly use the orderCreatedProducer
        messagePublisher.sendMessage(OutputBindings.ORDER_CREATED, orderCreatedEvent);
        LOGGER.info("The OrderCreatedEvent '{}' was published", orderCreatedEvent);
    }

    @Override
    public void publishChargeOrderCommand(final ChargeOrderCommand chargeOrderCommand) {
        //TODO find a way to directly use the chargeOrderProducer
        messagePublisher.sendMessage(OutputBindings.CHARGE_ORDER, chargeOrderCommand);
        LOGGER.info("The ChargeOrderCommand '{}' was published", chargeOrderCommand);
    }
}
