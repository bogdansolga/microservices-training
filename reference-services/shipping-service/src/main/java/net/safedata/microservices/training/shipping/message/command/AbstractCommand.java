package net.safedata.microservices.training.shipping.message.command;

import net.safedata.microservices.training.shipping.message.AbstractMessage;

abstract class AbstractCommand extends AbstractMessage {

    AbstractCommand(long messageId) {
        super(messageId);
    }
}
