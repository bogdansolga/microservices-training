package net.safedata.microservices.training.delivery.inbound.port;

import net.safedata.microservices.training.marker.port.InboundPort;
import net.safedata.microservices.training.message.command.order.DeliverOrderCommand;

public interface MessagingInboundPort extends InboundPort {

    void deliverOrder(DeliverOrderCommand deliverOrderCommand);
}
