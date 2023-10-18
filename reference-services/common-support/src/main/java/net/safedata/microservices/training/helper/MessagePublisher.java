package net.safedata.microservices.training.helper;

import net.safedata.microservices.training.message.AbstractMessage;
import net.safedata.microservices.training.message.Bindings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

    private final StreamBridge streamBridge;

    @Autowired
    public MessagePublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Async
    public <Message extends AbstractMessage<?>> void sendMessage(Bindings binding, Message message) {
        streamBridge.send(binding.getBindingName(), message);
    }
}
