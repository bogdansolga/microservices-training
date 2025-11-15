package net.safedata.spring.training.complete.project.service;

import net.safedata.spring.training.complete.project.model.Payment;
import net.safedata.spring.training.complete.project.model.PaymentStatus;
import net.safedata.spring.training.complete.project.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional(readOnly = true)
    public Payment findByOrderId(int orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order id: " + orderId));
    }

    @Transactional
    public Payment updatePaymentStatus(int paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }
}
