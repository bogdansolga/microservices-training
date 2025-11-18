package net.safedata.microservices.training.restaurant.outbound.port;

import net.safedata.microservices.training.marker.port.OutboundPort;
import net.safedata.microservices.training.message.command.order.DeliverOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;

public interface MessagingOutboundPort extends OutboundPort {
    void publishOrderProcessedEvent(OrderProcessedEvent orderProcessedEvent);

    void publishDeliverOrderCommand(DeliverOrderCommand deliverOrderCommand);
}
