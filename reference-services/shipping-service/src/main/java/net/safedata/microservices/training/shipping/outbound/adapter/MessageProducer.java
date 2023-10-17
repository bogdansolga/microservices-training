package net.safedata.microservices.training.shipping.outbound.adapter;

import net.safedata.microservices.training.marker.port.OutboundPort;
import net.safedata.microservices.training.message.event.order.OrderShippedEvent;
import net.safedata.microservices.training.shipping.outbound.port.MessagingOutboundPort;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer implements MessagingOutboundPort, OutboundPort {

    @Override
    public void publishOrderShippedEvent(final OrderShippedEvent orderShippedEvent) {
    }
}
