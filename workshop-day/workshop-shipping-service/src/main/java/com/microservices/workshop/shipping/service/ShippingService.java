package com.microservices.workshop.shipping.service;

import com.microservices.workshop.shipping.domain.entity.Shipping;
import com.microservices.workshop.shipping.domain.repository.ShippingRepository;
import com.microservices.workshop.shipping.dto.MessageDTO;
import com.microservices.workshop.shipping.dto.ShippingDTO;
import com.microservices.workshop.shipping.mapper.ShippingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShippingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingService.class);

    private final ShippingMapper shippingMapper;
    private final ShippingRepository shippingRepository;

    @Autowired
    public ShippingService(ShippingMapper shippingMapper, ShippingRepository shippingRepository) {
        this.shippingMapper = shippingMapper;
        this.shippingRepository = shippingRepository;
    }

    // absolutely mandatory on write operations (Create, Update, Delete)
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false, // for write operations
            noRollbackFor = IllegalArgumentException.class // depending on the business context
    )
    public MessageDTO ship(ShippingDTO shippingDTO) {
        // always perform the validations as the first step of the processing
        validate(shippingDTO);

        final Shipping shipping = shippingMapper.asEntity(shippingDTO);

        shippingRepository.save(shipping);
        LOGGER.info("The shipping with the ID {} was successfully saved", shippingDTO.getCustomerId());

        return new MessageDTO("The shipping with the ID " + shipping.getCustomerId() + " was charged");
    }

    private void validate(ShippingDTO shippingDTO) {
        // perform the validations
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = true
    )
    public MessageDTO get(int id) {
        throw new IllegalArgumentException("There is no charge with the ID " + id);
    }
}
