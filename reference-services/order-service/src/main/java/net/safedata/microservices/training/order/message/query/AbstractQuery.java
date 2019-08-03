package net.safedata.microservices.training.order.message.query;

import net.safedata.microservices.training.order.message.AbstractMessage;

abstract class AbstractQuery extends AbstractMessage {

    AbstractQuery(long messageId) {
        super(messageId);
    }
}
