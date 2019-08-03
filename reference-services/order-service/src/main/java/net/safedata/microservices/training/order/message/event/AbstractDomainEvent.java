package net.safedata.microservices.training.order.message.event;

import net.safedata.microservices.training.order.message.AbstractMessage;

public abstract class AbstractDomainEvent extends AbstractMessage {

    private final long eventId;

    public AbstractDomainEvent(long messageId, long eventId) {
        super(messageId);
        this.eventId = eventId;
    }

    public long getEventId() {
        return eventId;
    }
}
