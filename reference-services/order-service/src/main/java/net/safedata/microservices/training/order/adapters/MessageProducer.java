package net.safedata.microservices.training.order.adapters;

import net.safedata.microservices.training.order.channels.OutboundChannels;
import net.safedata.microservices.training.order.message.command.ChargeOrderCommand;
import net.safedata.microservices.training.order.message.command.ShipOrderCommand;
import net.safedata.microservices.training.order.message.event.OrderCreatedEvent;
import net.safedata.microservices.training.order.ports.MessagingOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(OutboundChannels.class)
public class MessageProducer implements MessagingOutboundPort {

    private final OutboundChannels outboundChannels;

    @Autowired
    public MessageProducer(final OutboundChannels outboundChannels) {
        this.outboundChannels = outboundChannels;
    }

    public void publishOrderCreatedEvent(final OrderCreatedEvent orderCreatedEvent) {
        outboundChannels.orderCreated()
                        .send(MessageCreator.create(orderCreatedEvent));
    }

    @Override
    public void publishChargeOrderCommand(final ChargeOrderCommand chargeOrderCommand) {
        outboundChannels.chargeOrder()
                        .send(MessageCreator.create(chargeOrderCommand));
    }

    @Override
    public void publishShipOrderCommand(final ShipOrderCommand shipOrderCommand) {
        outboundChannels.shipOrder()
                        .send(MessageCreator.create(shipOrderCommand));
    }
}
