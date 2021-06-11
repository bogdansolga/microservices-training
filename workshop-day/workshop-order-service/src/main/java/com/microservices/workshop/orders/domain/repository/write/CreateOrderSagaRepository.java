package com.microservices.workshop.orders.domain.repository.write;

import com.microservices.workshop.orders.domain.entity.write.CreateOrderSaga;
import org.springframework.data.repository.CrudRepository;

public interface CreateOrderSagaRepository extends CrudRepository<CreateOrderSaga, Long> {
}
