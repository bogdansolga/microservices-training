package net.safedata.microservices.training.helper;

import net.safedata.microservices.training.message.AbstractMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import java.util.UUID;

public final class MessageCreator {

    public static  <T extends AbstractMessage> Message<T> create(final T t) {
        return MessageBuilder.withPayload(t)
                             .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                             .setHeader("correlationId", UUID.randomUUID().toString())
                             .setHeader("version", "1.2")
                             .setErrorChannelName("error_channel_" + t.getName()) // just an ex
                             .setReplyChannelName("reply_channel_" + t.getName())
                             .build();
    }
}
