package net.safedata.microservices.training.order.adapter;

import net.safedata.microservices.training.order.dto.DailyMenuDTO;
import org.springframework.stereotype.Component;

@Component
public class MessagingInboundAdapter {

    public void publishDailyMenu(DailyMenuDTO dailyMenuDTO) {
        System.out.println("Today's daily menu is " + dailyMenuDTO.getName());
    }
}
