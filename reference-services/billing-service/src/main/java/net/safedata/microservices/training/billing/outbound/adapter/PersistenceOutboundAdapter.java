package net.safedata.microservices.training.billing.outbound.adapter;

import net.safedata.microservices.training.billing.domain.model.Payment;
import net.safedata.microservices.training.billing.domain.repository.PaymentRepository;
import net.safedata.microservices.training.billing.outbound.port.PersistenceOutboundPort;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersistenceOutboundAdapter implements PersistenceOutboundPort, OutboundAdapter {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PersistenceOutboundAdapter(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public long save(Payment payment) {
        return paymentRepository.save(payment).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<Payment> findByCustomerId(Long customerId) {
        return paymentRepository.findByCustomerId(customerId);
    }
}
