package net.safedata.microservices.training.billing.domain.repository;

import net.safedata.microservices.training.billing.domain.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    List<Payment> findByCustomerId(Long customerId);
}
