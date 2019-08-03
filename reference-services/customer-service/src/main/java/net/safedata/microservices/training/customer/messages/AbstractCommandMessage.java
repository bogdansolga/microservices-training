package net.safedata.microservices.training.customer.messages;

import java.io.Serializable;

public abstract class AbstractCommandMessage implements Serializable {

    private final long messageId;

    AbstractCommandMessage(final long messageId) {
        this.messageId = messageId;
    }

    public abstract String getName();

    public long getMessageId() {
        return messageId;
    }
}
