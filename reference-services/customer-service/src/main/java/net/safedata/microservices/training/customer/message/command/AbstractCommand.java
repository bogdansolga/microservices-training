package net.safedata.microservices.training.customer.message.command;

import net.safedata.microservices.training.customer.message.AbstractMessage;

abstract class AbstractCommand extends AbstractMessage {

    AbstractCommand(long messageId) {
        super(messageId);
    }
}
