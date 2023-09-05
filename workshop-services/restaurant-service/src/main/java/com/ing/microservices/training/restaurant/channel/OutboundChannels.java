package com.ing.microservices.training.restaurant.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutboundChannels {

    @Output("daily_menu")
    MessageChannel dailyMenu();
}