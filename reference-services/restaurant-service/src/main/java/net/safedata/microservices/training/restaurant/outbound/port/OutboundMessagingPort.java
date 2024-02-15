package net.safedata.microservices.training.restaurant.outbound.port;

import net.safedata.microservices.training.message.command.order.DeliverOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;

public interface OutboundMessagingPort {
    void publishOrderProcessedEvent(OrderProcessedEvent orderProcessedEvent);

    void publishDeliverOrderCommand(DeliverOrderCommand deliverOrderCommand);
}
