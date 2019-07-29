package net.safedata.microservices.training.ports;

import net.safedata.microservices.training.dto.OrderDTO;
import net.safedata.microservices.training.marker.InboundPort;
import net.safedata.microservices.training.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrdersController implements InboundPort {

    private final OrderService orderService;

    @Autowired
    public OrdersController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody final OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);
        return ResponseEntity.ok("OK");
    }
}
