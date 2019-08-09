package net.safedata.microservices.training.order.outbound.adapter;

import net.safedata.microservices.training.order.model.Order;
import net.safedata.microservices.training.order.outbound.port.PersistenceOutboundPort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>, PersistenceOutboundPort {
}
