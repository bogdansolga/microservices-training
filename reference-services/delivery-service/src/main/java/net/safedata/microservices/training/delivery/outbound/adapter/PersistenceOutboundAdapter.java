package net.safedata.microservices.training.delivery.outbound.adapter;

import net.safedata.microservices.training.delivery.domain.model.Delivery;
import net.safedata.microservices.training.delivery.domain.repository.DeliveryRepository;
import net.safedata.microservices.training.delivery.outbound.port.PersistenceOutboundPort;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersistenceOutboundAdapter implements PersistenceOutboundPort, OutboundAdapter {

    private final DeliveryRepository deliveryRepository;

    public PersistenceOutboundAdapter(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    @Transactional
    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Delivery> findById(Long id) {
        return deliveryRepository.findById(id);
    }
}
