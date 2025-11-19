package net.safedata.microservices.training.billing.inbound.adapter;

import net.safedata.microservices.training.dto.order.PaymentDTO;
import net.safedata.microservices.training.billing.inbound.port.RestInboundPort;
import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController implements InboundAdapter {

    private final RestInboundPort restInboundPort;

    @Autowired
    public BillingController(final RestInboundPort restInboundPort) {
        this.restInboundPort = restInboundPort;
    }

    @GetMapping("/{customerId}")
    public List<PaymentDTO> getPaymentsForCustomer(@PathVariable final long customerId) {
        return restInboundPort.getPaymentsForCustomer(customerId);
    }
}
