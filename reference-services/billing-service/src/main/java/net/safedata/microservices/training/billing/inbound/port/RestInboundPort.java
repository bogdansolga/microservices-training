package net.safedata.microservices.training.billing.inbound.port;

import net.safedata.microservices.training.marker.port.InboundPort;
import net.safedata.microservices.training.dto.order.PaymentDTO;

import java.util.List;

public interface RestInboundPort extends InboundPort {

    List<PaymentDTO> getPaymentsForCustomer(long customerId);
}
