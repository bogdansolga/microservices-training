package net.safedata.microservices.training.restaurant.inbound.port;

import net.safedata.microservices.training.marker.port.InboundPort;
import net.safedata.microservices.training.message.command.order.ProcessOrderCommand;

public interface InboundMessagingPort extends InboundPort {
    void processOrder(ProcessOrderCommand processOrderCommand);
}
