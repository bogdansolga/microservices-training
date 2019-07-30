package net.safedata.microservices.training.customer.service;

import net.safedata.microservices.training.customer.dto.CustomerDTO;
import net.safedata.microservices.training.customer.events.CustomerCreatedEvent;
import net.safedata.microservices.training.customer.marker.InboundPort;
import net.safedata.microservices.training.customer.ports.MessagingOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService implements InboundPort {

    private final MessagingOutboundPort messagingOutboundPort;

    @Autowired
    public CustomerService(final MessagingOutboundPort messagingOutboundPort) {
        this.messagingOutboundPort = messagingOutboundPort;
    }

    @Transactional
    public void createCustomer(CustomerDTO customerDTO) {
        // insert magic here
        messagingOutboundPort.publishEvent(new CustomerCreatedEvent(230));
    }
}
