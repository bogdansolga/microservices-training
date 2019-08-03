package net.safedata.microservices.training.order.adapters;

import net.safedata.microservices.training.order.dto.OrderDTO;
import net.safedata.microservices.training.order.marker.InboundAdapter;
import net.safedata.microservices.training.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController implements InboundAdapter {

    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody final OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);

        return ResponseEntity.ok("The customer was successfully created");
    }
}
