package net.safedata.microservices.training.customer.message.query;

import net.safedata.microservices.training.customer.message.AbstractMessage;

abstract class AbstractQuery extends AbstractMessage {

    AbstractQuery(long messageId) {
        super(messageId);
    }
}
