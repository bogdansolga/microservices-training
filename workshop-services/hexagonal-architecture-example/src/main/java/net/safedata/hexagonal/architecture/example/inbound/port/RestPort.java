package net.safedata.hexagonal.architecture.example.inbound.port;

import net.safedata.hexagonal.architecture.example.service.BusinessLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Simple example of a REST `port` - a class that is:
 *  - invoked by the REST Adapter
 *  - perform any additional / intermediary operations
 *  - invoking the business logic
 */
@Component
public class RestPort {

    // the RestPort collaborates / invokes the BusinessLogicService class
    private final BusinessLogicService businessLogicService;

    @Autowired
    public RestPort(BusinessLogicService businessLogicService) {
        this.businessLogicService = businessLogicService;
    }

    public ResponseEntity<String> businessOperation(String value) {
        return businessLogicService.businessOperation(value);
    }
}
