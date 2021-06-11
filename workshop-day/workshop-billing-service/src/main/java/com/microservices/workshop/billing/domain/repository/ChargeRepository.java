package com.microservices.workshop.billing.domain.repository;

import com.microservices.workshop.billing.domain.entity.Charge;
import org.springframework.data.repository.CrudRepository;

public interface ChargeRepository extends CrudRepository<Charge, Long> {
}
