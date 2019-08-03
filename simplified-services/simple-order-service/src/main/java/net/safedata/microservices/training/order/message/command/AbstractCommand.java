package net.safedata.microservices.training.order.message.command;

import net.safedata.microservices.training.order.message.AbstractMessage;

abstract class AbstractCommand extends AbstractMessage {

    AbstractCommand(long messageId) {
        super(messageId);
    }
}
