package net.safedata.microservices.training.customer.inbound.adapter;

import net.safedata.microservices.training.dto.customer.CustomerDTO;
import net.safedata.microservices.training.customer.service.CustomerService;
import net.safedata.microservices.training.marker.adapter.InboundAdapter;
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
    public ResponseEntity<String> createCustomer(@RequestBody final CustomerDTO customerDTO) {
        customerService.createCustomer(customerDTO);

        return ResponseEntity.ok("The customer was successfully created");
    }
}
