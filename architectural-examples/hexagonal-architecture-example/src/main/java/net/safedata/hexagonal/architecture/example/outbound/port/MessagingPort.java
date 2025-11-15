package net.safedata.hexagonal.architecture.example.outbound.port;

import net.safedata.hexagonal.architecture.example.outbound.adapter.MessagingAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagingPort {

    private final MessagingAdapter messagingAdapter;

    @Autowired
    public MessagingPort(MessagingAdapter messagingAdapter) {
        this.messagingAdapter = messagingAdapter;
    }

    public void publishMessage(final String message) {
        messagingAdapter.publishMessage(message);
    }
}
