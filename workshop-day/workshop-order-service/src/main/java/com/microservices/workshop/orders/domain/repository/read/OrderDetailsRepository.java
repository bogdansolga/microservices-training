package com.microservices.workshop.orders.domain.repository.read;

import com.microservices.workshop.orders.domain.entity.read.OrderDetails;
import com.microservices.workshop.orders.domain.entity.write.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Long> {
}
