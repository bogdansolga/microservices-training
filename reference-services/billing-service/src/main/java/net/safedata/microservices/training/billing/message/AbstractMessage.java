package net.safedata.microservices.training.billing.message;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class AbstractMessage implements Serializable {

    private final long messageId;
    private final LocalDateTime creationDateTime;

    public AbstractMessage(final long messageId) {
        this.messageId = messageId;
        this.creationDateTime = LocalDateTime.now();
    }

    public abstract String getName();

    public long getMessageId() {
        return messageId;
    }

    public LocalDateTime creationDateTime() {
        return creationDateTime;
    }
}
