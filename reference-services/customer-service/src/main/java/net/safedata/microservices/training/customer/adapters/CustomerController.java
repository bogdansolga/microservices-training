package net.safedata.microservices.training.customer.adapters;

import net.safedata.microservices.training.customer.dto.CustomerDTO;
import net.safedata.microservices.training.customer.marker.InboundAdapter;
import net.safedata.microservices.training.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController implements InboundAdapter {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody final CustomerDTO customerDTO) {
        customerService.createCustomer(customerDTO);
        return ResponseEntity.ok("OK");
    }
}
