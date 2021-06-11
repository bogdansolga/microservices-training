package com.microservices.workshop.orders.controller;

import com.microservices.workshop.orders.dto.MessageDTO;
import com.microservices.workshop.orders.dto.OrderDTO;
import com.microservices.workshop.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public MessageDTO createOrder(@RequestBody final OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @GetMapping("/{id}")
    public MessageDTO getOrder(@PathVariable final int id) {
        return orderService.get(id);
    }
}
