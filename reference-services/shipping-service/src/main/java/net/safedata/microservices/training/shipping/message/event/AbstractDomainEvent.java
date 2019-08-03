package net.safedata.microservices.training.shipping.message.event;

import net.safedata.microservices.training.shipping.message.AbstractMessage;

abstract class AbstractDomainEvent extends AbstractMessage {

    private final long eventId;

    AbstractDomainEvent(long messageId, long eventId) {
        super(messageId);
        this.eventId = eventId;
    }

    public long getEventId() {
        return eventId;
    }
}
