package net.safedata.microservices.training.order.inbound;

import net.safedata.microservices.training.dto.order.OrderDTO;
import net.safedata.microservices.training.order.inbound.port.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody final OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);

        return ResponseEntity.ok("The order was successfully created");
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Long orderId, @RequestBody final OrderDTO orderDTO) {
        orderService.updateOrder(orderId, orderDTO);

        return ResponseEntity.ok("The order was successfully created");
    }
}
