package com.microservices.workshop.orders.domain.repository;

import com.microservices.workshop.orders.domain.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
