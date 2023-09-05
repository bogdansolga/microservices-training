package com.ing.microservices.training.order.adapter;

import com.ing.microservices.training.order.channel.InboundChannels;
import com.ing.microservices.training.order.dto.DailyMenuDTO;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(InboundChannels.class)
public class MessagingInboundAdapter {

    @StreamListener("daily_menu")
    public void publishDailyMenu(DailyMenuDTO dailyMenuDTO) {
        System.out.println("Today's daily menu is " + dailyMenuDTO.getName());
    }
}
