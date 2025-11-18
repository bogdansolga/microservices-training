package net.safedata.microservices.training.billing.outbound.adapter;

import net.safedata.microservices.training.billing.domain.model.Payment;
import net.safedata.microservices.training.billing.domain.repository.PaymentRepository;
import net.safedata.microservices.training.billing.outbound.port.PersistenceOutboundPort;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersistenceOutboundAdapter implements PersistenceOutboundPort, OutboundAdapter {

    private final PaymentRepository paymentRepository;

    public PersistenceOutboundAdapter(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }
}
