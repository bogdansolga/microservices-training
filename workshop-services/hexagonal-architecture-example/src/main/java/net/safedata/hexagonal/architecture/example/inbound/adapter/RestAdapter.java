package net.safedata.hexagonal.architecture.example.inbound.adapter;

import net.safedata.hexagonal.architecture.example.inbound.port.RestPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple adapter for REST communication
 */
@RestController
@RequestMapping("/hexagonal")
public class RestAdapter {

    // the RestAdapter uses a RestPort
    private final RestPort restPort;

    @Autowired
    public RestAdapter(RestPort restPort) {
        this.restPort = restPort;
    }

    @GetMapping("/{value}")
    public ResponseEntity<String> businessOperation(@PathVariable String value) {
        return restPort.businessOperation(value);
    }
}
