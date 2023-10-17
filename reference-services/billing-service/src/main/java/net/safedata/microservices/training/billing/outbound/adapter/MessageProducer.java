package net.safedata.microservices.training.billing.outbound.adapter;

import net.safedata.microservices.training.billing.outbound.producer.OrderChargedProducer;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.billing.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements MessagingOutboundPort, OutboundAdapter {

    private final OrderChargedProducer orderChargedProducer;

    @Autowired
    public MessageProducer(final OrderChargedProducer orderChargedProducer) {
        this.orderChargedProducer = orderChargedProducer;
    }

    @Override
    public void publishOrderChargedEvent(final OrderChargedEvent orderChargedEvent) {
        orderChargedProducer.apply(orderChargedEvent);
    }

    @Override
    public void publishOrderNotChargedEvent(final OrderNotChargedEvent orderNotChargedEvent) {
    }
}
