package com.epic.eats.repository;

import com.epic.eats.model.Delivery;
import com.epic.eats.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    Optional<Delivery> findByOrderId(int orderId);

    List<Delivery> findByStatus(DeliveryStatus status);

    List<Delivery> findByDeliveryPersonName(String deliveryPersonName);
}
