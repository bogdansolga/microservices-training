package net.safedata.microservices.training.customer.domain.repository;

import net.safedata.microservices.training.customer.domain.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
