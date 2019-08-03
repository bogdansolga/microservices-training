package net.safedata.microservices.training.billing.message.query;

import net.safedata.microservices.training.billing.message.AbstractMessage;

abstract class AbstractQuery extends AbstractMessage {

    AbstractQuery(long messageId) {
        super(messageId);
    }
}
