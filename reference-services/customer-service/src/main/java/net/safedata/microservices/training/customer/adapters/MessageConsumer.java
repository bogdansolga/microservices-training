package net.safedata.microservices.training.customer.adapters;

import net.safedata.microservices.training.customer.channels.InboundChannels;
import net.safedata.microservices.training.customer.dto.CustomerDTO;
import net.safedata.microservices.training.customer.marker.InboundAdapter;
import net.safedata.microservices.training.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(InboundChannels.class)
public class MessageConsumer implements InboundAdapter {

    private final CustomerService customerService;

    @Autowired
    public MessageConsumer(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @StreamListener(InboundChannels.CUSTOMER)
    public void createOrder(final CustomerDTO customerDTO) {
        customerService.createCustomer(customerDTO);
    }
}
