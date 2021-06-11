package com.microservices.workshop.orders.domain.repository.write;

import com.microservices.workshop.orders.domain.entity.write.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
