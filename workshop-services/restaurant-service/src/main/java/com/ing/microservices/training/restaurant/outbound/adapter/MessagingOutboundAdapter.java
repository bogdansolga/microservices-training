package com.ing.microservices.training.restaurant.outbound.adapter;

import com.ing.microservices.training.restaurant.channel.OutboundChannels;
import com.ing.microservices.training.restaurant.dto.DailyMenuDTO;
import com.ing.microservices.training.restaurant.helper.MessageCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(OutboundChannels.class)
public class MessagingOutboundAdapter {

    private final OutboundChannels outboundChannels;

    @Autowired
    public MessagingOutboundAdapter(final OutboundChannels outboundChannels) {
        this.outboundChannels = outboundChannels;
    }

    public void publishDailyMenu(DailyMenuDTO dailyMenuDTO) {
        outboundChannels.dailyMenu()
                        .send(MessageCreator.create(dailyMenuDTO));
    }
}
