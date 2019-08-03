package net.safedata.microservices.training.customer.messages.command;

import net.safedata.microservices.training.customer.messages.AbstractMessage;

abstract class AbstractCommand extends AbstractMessage {

    AbstractCommand(long messageId) {
        super(messageId);
    }
}
