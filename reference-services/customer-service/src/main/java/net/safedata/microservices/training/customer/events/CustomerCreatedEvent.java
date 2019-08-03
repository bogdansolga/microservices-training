package net.safedata.microservices.training.customer.events;

import java.time.LocalDateTime;

public class CustomerCreatedEvent extends AbstractDomainEvent {

    private static final String NAME = "CustomerCreated";

    private final long customerId;
    private final String customerEmail;
    private final LocalDateTime creationTimeDate;

    public CustomerCreatedEvent(final long eventId, final long customerId, final String customerEmail) {
        super(eventId);
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.creationTimeDate = LocalDateTime.now();
    }

    @Override
    public String getName() {
        return NAME;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public LocalDateTime getCreationTimeDate() {
        return creationTimeDate;
    }
}
