package net.safedata.microservices.training.order.domain.repository;

import net.safedata.microservices.training.order.domain.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
