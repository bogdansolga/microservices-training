package net.safedata.microservices.training.billing.message.command;

import net.safedata.microservices.training.billing.message.AbstractMessage;

import java.time.LocalDateTime;

abstract class AbstractCommand extends AbstractMessage {

    AbstractCommand(long messageId) {
        super(messageId);
    }
}
