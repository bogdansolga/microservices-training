package net.safedata.microservices.training.billing.inbound.adapter;

import net.safedata.microservices.training.dto.PaymentDTO;
import net.safedata.microservices.training.billing.inbound.port.BillingService;
import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController implements InboundAdapter {

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
