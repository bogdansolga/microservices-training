package net.safedata.microservices.training.billing.domain.repository;

import net.safedata.microservices.training.billing.domain.model.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
