package net.safedata.microservices.training.delivery.inbound.port;

import net.safedata.microservices.training.message.command.order.DeliverOrderCommand;

public interface InboundMessagingPort {

    void deliverOrder(DeliverOrderCommand deliverOrderCommand);
}
