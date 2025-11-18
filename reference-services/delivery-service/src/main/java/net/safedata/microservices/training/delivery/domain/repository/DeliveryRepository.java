package net.safedata.microservices.training.delivery.domain.repository;

import net.safedata.microservices.training.delivery.domain.model.Delivery;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
}
