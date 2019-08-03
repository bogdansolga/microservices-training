package net.safedata.microservices.training.billing.adapter;

import net.safedata.microservices.training.billing.dto.PaymentDTO;
import net.safedata.microservices.training.billing.marker.adapter.Adapter;
import net.safedata.microservices.training.billing.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController implements Adapter {

    private final BillingService billingService;

    @Autowired
    public BillingController(final BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/{customerId}")
    public List<PaymentDTO> getPaymentsForCustomer(@PathVariable final long customerId) {
        return billingService.getPaymentsForCustomer(customerId);
    }
}
