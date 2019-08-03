package net.safedata.microservices.training.customer.adapter;

import net.safedata.microservices.training.customer.message.AbstractMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

final class MessageCreator {

    static  <T extends AbstractMessage> Message<?> create(final T t) {
        return MessageBuilder.withPayload(t)
                             .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                             .build();
    }
}
