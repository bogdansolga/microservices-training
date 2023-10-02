package com.ing.microservices.training.order.helper;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import java.util.UUID;

public final class MessageCreator {

    @SuppressWarnings("rawtypes")
    public static <Payload> Message<Payload> create(final Payload payload) {
        return MessageBuilder.withPayload(payload)
                             .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                             .setHeader("correlationId", UUID.randomUUID().toString())
                             .build();
    }
}
