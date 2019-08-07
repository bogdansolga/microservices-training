package net.safedata.microservices.training.customer.inbound.port;

import net.safedata.microservices.training.dto.customer.CustomerDTO;
import net.safedata.microservices.training.marker.port.InboundPort;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements InboundPort {

    public void createCustomer(final CustomerDTO customerDTO) {
        //TODO implement me
    }
}
