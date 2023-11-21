package net.safedata.microservices.training.order.inbound.port;

import net.safedata.microservices.training.dto.order.OrderDTO;
import net.safedata.microservices.training.marker.port.InboundPort;

public interface RestInboundPort extends InboundPort {

    void createOrder(final OrderDTO orderDTO);
}
