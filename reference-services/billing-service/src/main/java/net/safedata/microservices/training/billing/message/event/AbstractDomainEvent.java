package net.safedata.microservices.training.billing.message.event;

import net.safedata.microservices.training.billing.message.AbstractMessage;

import java.time.LocalDateTime;

public abstract class AbstractDomainEvent extends AbstractMessage {

    private final long eventId;

    AbstractDomainEvent(long messageId, long eventId) {
        super(messageId);
        this.eventId = eventId;
    }

    public long getEventId() {
        return eventId;
    }
}
