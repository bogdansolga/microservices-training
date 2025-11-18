package net.safedata.microservices.training.delivery.outbound.port;

import net.safedata.microservices.training.delivery.domain.model.Delivery;
import net.safedata.microservices.training.marker.port.OutboundPort;

import java.util.Optional;

public interface PersistenceOutboundPort extends OutboundPort {

    long save(Delivery delivery);

    Optional<Delivery> findById(Long id);
}
