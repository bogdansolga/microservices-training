package net.safedata.microservices.training.customer.service;

import net.safedata.microservices.training.customer.dto.CustomerDTO;
import net.safedata.microservices.training.customer.events.CustomerCreatedEvent;
import net.safedata.microservices.training.customer.events.CustomerUpdatedEvent;
import net.safedata.microservices.training.customer.marker.InboundPort;
import net.safedata.microservices.training.customer.events.OrderCreatedEvent;
import net.safedata.microservices.training.customer.ports.MessagingOutboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class CustomerService implements InboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final MessagingOutboundPort messagingOutboundPort;

    @Autowired
    public CustomerService(final MessagingOutboundPort messagingOutboundPort) {
        this.messagingOutboundPort = messagingOutboundPort;
    }

    @Transactional
    public void createCustomer(final CustomerDTO customerDTO) {
        LOGGER.info("Creating the customer '{}' [{}]...", customerDTO.getName(), customerDTO.getEmail());

        final long customerId = saveCustomerInTheDatabase(customerDTO);

        // insert magic here
        messagingOutboundPort.publishCustomerCreatedEvent(
                new CustomerCreatedEvent(getNextEventId(), customerId, customerDTO.getEmail()));
    }

    private long saveCustomerInTheDatabase(final CustomerDTO customerDTO) {
        // TODO insert magic here
        sleepALittle();

        return new Random(10000).nextLong();
    }

    @Transactional
    public void handleOrderCreated(final OrderCreatedEvent orderCreatedMessage) {
        final long customerId = orderCreatedMessage.getCustomerId();

        LOGGER.info("Updating the customer with the ID {}...", customerId);
        updateCustomer(customerId);

        messagingOutboundPort.publishCustomerUpdatedEvent(new CustomerUpdatedEvent(customerId));
    }

    private void updateCustomer(long customerId) {
        // TODO insert magic here
        sleepALittle();
    }

    private long getNextEventId() {
        // returned from the saved database event, before sending it (using transactional messaging)
        return new Random(900000).nextLong();
    }

    // simulate a long running operation
    private void sleepALittle() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
