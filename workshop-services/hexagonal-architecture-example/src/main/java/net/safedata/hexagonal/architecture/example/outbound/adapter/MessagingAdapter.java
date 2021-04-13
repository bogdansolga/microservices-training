package net.safedata.hexagonal.architecture.example.outbound.adapter;

import org.springframework.stereotype.Component;

@Component
public class MessagingAdapter {

    public void publishMessage(final String message) {
        System.out.println("Publishing the message '" + message + "'...");
    }
}
