package com.ing.microservices.training.restaurant;

import com.ing.microservices.training.restaurant.dto.DailyMenuDTO;
import com.ing.microservices.training.restaurant.outbound.adapter.MessagingOutboundAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class DailyMenuController {

    private final MessagingOutboundAdapter messagingOutboundAdapter;

    @Autowired
    public DailyMenuController(MessagingOutboundAdapter messagingOutboundAdapter) {
        this.messagingOutboundAdapter = messagingOutboundAdapter;
    }

    @GetMapping
    public void publishMessage() {
        DailyMenuDTO dailyMenu = new DailyMenuDTO("Pizza for the ladies", 2.5, "Cheesy pizza");
        messagingOutboundAdapter.publishDailyMenu(dailyMenu);
        System.out.println("The message was published");
    }
}
