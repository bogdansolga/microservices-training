package net.safedata.microservices.training.customer.messages;

import java.io.Serializable;

public abstract class AbstractMessage implements Serializable {

    private final long messageId;

    AbstractMessage(final long messageId) {
        this.messageId = messageId;
    }

    public abstract String getName();

    public long getMessageId() {
        return messageId;
    }
}
