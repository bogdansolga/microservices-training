package com.microservices.workshop.shipping.controller;

import com.microservices.workshop.shipping.dto.MessageDTO;
import com.microservices.workshop.shipping.dto.ShippingDTO;
import com.microservices.workshop.shipping.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingService shippingService;

    @Autowired
    public ShippingController(final ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping
    public MessageDTO ship(@RequestBody final ShippingDTO shippingDTO) {
        return shippingService.ship(shippingDTO);
    }

    @GetMapping("/{id}")
    public MessageDTO getCharge(@PathVariable final int id) {
        return shippingService.get(id);
    }
}
