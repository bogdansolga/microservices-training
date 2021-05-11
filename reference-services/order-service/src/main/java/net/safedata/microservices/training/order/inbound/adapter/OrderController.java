package net.safedata.microservices.training.order.inbound.adapter;

import net.safedata.microservices.training.dto.order.OrderDTO;
import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.order.inbound.port.OrderService;
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

        return ResponseEntity.ok("The order was successfully created");
    }
}
