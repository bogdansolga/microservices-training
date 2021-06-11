package com.microservices.workshop.shipping.domain.repository;

import com.microservices.workshop.shipping.domain.entity.Shipping;
import org.springframework.data.repository.CrudRepository;

public interface ShippingRepository extends CrudRepository<Shipping, Long> {
}
