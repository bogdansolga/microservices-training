package net.safedata.hexagonal.architecture.example.service;

import net.safedata.hexagonal.architecture.example.outbound.port.MessagingPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BusinessLogicService {

    private final MessagingPort messagingPort;

    @Autowired
    public BusinessLogicService(MessagingPort messagingPort) {
        this.messagingPort = messagingPort;
    }

    public ResponseEntity<String> businessOperation(String value) {
        System.out.println("Performing the application 'magic'...");

        messagingPort.publishMessage("OrderCreated");

        return ResponseEntity.ok("The request value is '" + value + "'");
    }
}
