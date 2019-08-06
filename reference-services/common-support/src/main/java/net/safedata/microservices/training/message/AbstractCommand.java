package net.safedata.microservices.training.message;

public abstract class AbstractCommand extends AbstractMessage<AbstractMessageType.CommandMessage> {

    public AbstractCommand(long messageId) {
        super(messageId);
    }
}
