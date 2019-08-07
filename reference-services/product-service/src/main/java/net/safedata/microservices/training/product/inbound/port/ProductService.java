package net.safedata.microservices.training.product.inbound.port;

import net.safedata.microservices.training.dto.product.ProductDTO;
import net.safedata.microservices.training.marker.port.InboundPort;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ProductService implements InboundPort {

    public ProductDTO getById(long productId) {
        return null;
    }

    private long getNextMessageId() {
        return new Random(900000).nextLong();
    }

    private long getNextEventId() {
        // returned from the saved database event, before sending it (using transactional messaging)
        return new Random(900000).nextLong();
    }

    // simulate a long running operation
    private void sleepALittle() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
