package net.safedata.microservices.training.billing.outbound.port;

import net.safedata.microservices.training.billing.domain.model.Payment;
import net.safedata.microservices.training.marker.port.OutboundPort;

import java.util.Optional;

public interface PersistenceOutboundPort extends OutboundPort {

    Payment save(Payment payment);

    Optional<Payment> findById(Long id);
}
