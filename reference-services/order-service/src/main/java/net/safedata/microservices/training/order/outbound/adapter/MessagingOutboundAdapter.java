package net.safedata.microservices.training.order.outbound.adapter;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import net.safedata.microservices.training.order.channel.OutboundChannels;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import net.safedata.microservices.training.message.command.order.ShipOrderCommand;
import net.safedata.microservices.training.order.outbound.port.MessagingOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(OutboundChannels.class)
public class MessagingOutboundAdapter implements MessagingOutboundPort, OutboundAdapter {

    private final OutboundChannels outboundChannels;

    @Autowired
    public MessagingOutboundAdapter(final OutboundChannels outboundChannels) {
        this.outboundChannels = outboundChannels;
    }

    @Override
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
