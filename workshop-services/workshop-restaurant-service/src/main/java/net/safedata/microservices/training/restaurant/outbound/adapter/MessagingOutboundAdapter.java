package net.safedata.microservices.training.restaurant.outbound.adapter;

import net.safedata.microservices.training.restaurant.dto.DailyMenuDTO;
import org.springframework.stereotype.Component;

@Component
public class MessagingOutboundAdapter {

    public void publishDailyMenu(DailyMenuDTO dailyMenuDTO) {
    }
}
