package net.safedata.microservices.training.order.outbound.adapter;

import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import net.safedata.microservices.training.message.event.order.OrderCreatedEvent;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import net.safedata.microservices.training.message.command.order.ShipOrderCommand;
import net.safedata.microservices.training.order.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.order.outbound.producer.ChargeOrderProducer;
import net.safedata.microservices.training.order.outbound.producer.OrderCreatedProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class MessagingOutboundAdapter implements MessagingOutboundPort, OutboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingOutboundAdapter.class);

    private final OrderCreatedProducer orderCreatedProducer;
    private final ChargeOrderProducer chargeOrderProducer;
    private final StreamBridge streamBridge;

    @Autowired
    public MessagingOutboundAdapter(OrderCreatedProducer orderCreatedProducer, ChargeOrderProducer chargeOrderProducer,
                                    StreamBridge streamBridge) {
        this.orderCreatedProducer = orderCreatedProducer;
        this.chargeOrderProducer = chargeOrderProducer;
        this.streamBridge = streamBridge;
    }

    @Override
    public void publishOrderCreatedEvent(final OrderCreatedEvent orderCreatedEvent) {
        //TODO find a way to directly return the return value of orderCreatedProducer.apply(orderCreatedEvent)
        streamBridge.send("orderCreatedProducer-out-0", orderCreatedProducer.apply(orderCreatedEvent));
        LOGGER.info("The OrderCreatedEvent '{}' was published", orderCreatedEvent);
    }

    @Override
    public void publishChargeOrderCommand(final ChargeOrderCommand chargeOrderCommand) {
        //TODO find a way to directly return the return value of chargeOrderProducer.apply(chargeOrderCommand)
        streamBridge.send("chargeOrderProducer-out-0", chargeOrderProducer.apply(chargeOrderCommand));
        LOGGER.info("The ChargeOrderCommand '{}' was published", chargeOrderCommand);
    }

    @Override
    public void publishShipOrderCommand(final ShipOrderCommand shipOrderCommand) {
        //TODO to be implemented
    }
}
