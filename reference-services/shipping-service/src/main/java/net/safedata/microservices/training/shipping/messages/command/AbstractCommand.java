package net.safedata.microservices.training.shipping.messages.command;

import net.safedata.microservices.training.shipping.messages.AbstractMessage;

abstract class AbstractCommand extends AbstractMessage {

    AbstractCommand(long messageId) {
        super(messageId);
    }
}
