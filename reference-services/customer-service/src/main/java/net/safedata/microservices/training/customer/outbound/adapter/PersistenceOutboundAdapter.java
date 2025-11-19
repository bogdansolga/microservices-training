package net.safedata.microservices.training.customer.outbound.adapter;

import net.safedata.microservices.training.customer.domain.model.Customer;
import net.safedata.microservices.training.customer.domain.repository.CustomerRepository;
import net.safedata.microservices.training.customer.outbound.port.PersistenceOutboundPort;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersistenceOutboundAdapter implements PersistenceOutboundPort, OutboundAdapter {

    private final CustomerRepository customerRepository;

    @Autowired
    public PersistenceOutboundAdapter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public long save(Customer customer) {
        return customerRepository.save(customer).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }
}
