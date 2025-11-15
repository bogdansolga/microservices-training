package net.safedata.microservices.training.order.outbound.port;

import net.safedata.microservices.training.marker.port.OutboundPort;
import net.safedata.microservices.training.order.domain.model.Order;

import java.util.Optional;

public interface PersistenceOutboundPort extends OutboundPort {
    long save(Order order);

    Optional<Order> findById(long orderId);
}
