package net.safedata.microservices.training.customer.outbound.port;

import net.safedata.microservices.training.customer.domain.model.Customer;
import net.safedata.microservices.training.marker.port.OutboundPort;

import java.util.Optional;

public interface PersistenceOutboundPort extends OutboundPort {
    long save(Customer customer);
    Optional<Customer> findById(Long id);
}
