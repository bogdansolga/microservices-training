package net.safedata.microservices.training.message;

public abstract class AbstractCommand extends AbstractMessage<AbstractMessageType.DomainEventMessage> {

    public AbstractCommand(long messageId) {
        super(messageId);
    }
}
