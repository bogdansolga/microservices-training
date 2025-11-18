package net.safedata.microservices.training.customer.inbound.port;

import net.safedata.microservices.training.customer.domain.model.Customer;
import net.safedata.microservices.training.customer.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.customer.outbound.port.PersistenceOutboundPort;
import net.safedata.microservices.training.dto.customer.CustomerDTO;
import net.safedata.microservices.training.marker.port.InboundPort;
import net.safedata.microservices.training.message.command.order.ProcessOrderCommand;
import net.safedata.microservices.training.message.event.customer.CustomerCreatedEvent;
import net.safedata.microservices.training.message.event.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class CustomerService implements InboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    // Sequential ID generators for realistic ID generation
    private static final AtomicLong MESSAGE_ID_COUNTER = new AtomicLong(2000);
    private static final AtomicLong EVENT_ID_COUNTER = new AtomicLong(6000);

    private final MessagingOutboundPort messagingOutboundPort;
    private final PersistenceOutboundPort persistenceOutboundPort;

    public CustomerService(MessagingOutboundPort messagingOutboundPort,
                          PersistenceOutboundPort persistenceOutboundPort) {
        this.messagingOutboundPort = messagingOutboundPort;
        this.persistenceOutboundPort = persistenceOutboundPort;
    }

    public void createCustomer(CustomerDTO customerDTO) {
        LOGGER.info("Creating customer: {}", customerDTO.getName());

        // Create and persist customer entity
        Customer customer = new Customer(
            customerDTO.getName(),
            customerDTO.getEmail(),
            null  // address not in DTO
        );
        long customerId = persistenceOutboundPort.save(customer);

        LOGGER.info("Customer persisted with id: {}", customerId);

        // Publish customer created event
        messagingOutboundPort.publishCustomerCreatedEvent(
            new CustomerCreatedEvent(getNextMessageId(), getNextEventId(), customerId, customerDTO.getEmail()));
    }

    public void handleOrderCreated(OrderCreatedEvent event) {
        LOGGER.info("Handling OrderCreatedEvent for customer: {}", event.getCustomerId());
        messagingOutboundPort.publishProcessOrderCommand(
            new ProcessOrderCommand(event.getCustomerId(), getNextMessageId(), "order", 100.0));
    }

    public void handleOrderCharged(OrderChargedEvent event) {
        LOGGER.info("Handling OrderChargedEvent: {}", event);
    }

    public void handleOrderNotCharged(OrderNotChargedEvent event) {
        LOGGER.info("Handling OrderNotChargedEvent: {}", event);
    }

    public void handleOrderProcessed(OrderProcessedEvent event) {
        LOGGER.info("Handling OrderProcessedEvent: {}", event);
    }

    public void handleOrderDelivered(OrderDeliveredEvent event) {
        LOGGER.info("Handling OrderDeliveredEvent: {}", event);
    }

    private long getNextMessageId() {
        return MESSAGE_ID_COUNTER.incrementAndGet();
    }

    private long getNextEventId() {
        return EVENT_ID_COUNTER.incrementAndGet();
    }
}
