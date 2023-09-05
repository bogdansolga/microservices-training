package com.ing.microservices.training.order.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InboundChannels {

    @Input("daily_menu")
    MessageChannel dailyMenu();
}