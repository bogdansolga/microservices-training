package net.safedata.microservices.training.customer.events;

import java.io.Serializable;

public abstract class AbstractDomainEvent implements Serializable {

    private final long eventId;

    AbstractDomainEvent(long eventId) {
        this.eventId = eventId;
    }

    public abstract String getName();

    public long getEventId() {
        return eventId;
    }
}
