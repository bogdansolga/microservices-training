package net.safedata.microservices.training.order.events;

import java.io.Serializable;

public abstract class AbstractDomainEvent implements Serializable {

    private final long eventId;

    AbstractDomainEvent(long eventId) {
        this.eventId = eventId;
    }

    public long getEventId() {
        return eventId;
    }

    public abstract String getName();
}
