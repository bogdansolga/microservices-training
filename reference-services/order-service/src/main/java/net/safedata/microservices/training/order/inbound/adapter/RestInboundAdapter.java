package net.safedata.microservices.training.order.inbound.adapter;

import net.safedata.microservices.training.dto.order.OrderDTO;
import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.order.inbound.port.RestInboundPort;
import net.safedata.microservices.training.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class RestInboundAdapter implements InboundAdapter {

    private final RestInboundPort restInboundPort;

    @Autowired
    public RestInboundAdapter(final RestInboundPort restInboundPort) {
        this.restInboundPort = restInboundPort;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody final OrderDTO orderDTO) {
        restInboundPort.createOrder(orderDTO);

        return ResponseEntity.ok("The order was successfully created");
    }
}
