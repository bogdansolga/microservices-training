package com.epic.eats.service;

import com.epic.eats.model.Delivery;
import com.epic.eats.model.DeliveryStatus;
import com.epic.eats.model.Order;
import com.epic.eats.model.OrderStatus;
import com.epic.eats.repository.DeliveryRepository;
import com.epic.eats.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, OrderRepository orderRepository) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Delivery createDelivery(int orderId, String deliveryAddress) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Check if delivery already exists for this order
        if (deliveryRepository.findByOrderId(orderId).isPresent()) {
            throw new RuntimeException("Delivery already exists for order: " + orderId);
        }

        Delivery delivery = new Delivery(order, deliveryAddress);
        return deliveryRepository.save(delivery);
    }

    @Transactional(readOnly = true)
    public Delivery findById(int id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Delivery findByOrderId(int orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Delivery not found for order: " + orderId));
    }

    @Transactional(readOnly = true)
    public List<Delivery> findByStatus(DeliveryStatus status) {
        return deliveryRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Delivery> findByDeliveryPerson(String deliveryPersonName) {
        return deliveryRepository.findByDeliveryPersonName(deliveryPersonName);
    }

    @Transactional
    public Delivery assignDeliveryPerson(int deliveryId, String deliveryPersonName,
                                          String deliveryPersonPhone, LocalDateTime estimatedDeliveryTime) {
        Delivery delivery = findById(deliveryId);

        if (delivery.getStatus() != DeliveryStatus.PENDING) {
            throw new RuntimeException("Cannot assign delivery person. Current status: " + delivery.getStatus());
        }

        delivery.setDeliveryPersonName(deliveryPersonName);
        delivery.setDeliveryPersonPhone(deliveryPersonPhone);
        delivery.setEstimatedDeliveryTime(estimatedDeliveryTime);
        delivery.setStatus(DeliveryStatus.ASSIGNED);
        delivery.setAssignedAt(LocalDateTime.now());

        return deliveryRepository.save(delivery);
    }

    @Transactional
    public Delivery markAsPickedUp(int deliveryId) {
        Delivery delivery = findById(deliveryId);

        if (delivery.getStatus() != DeliveryStatus.ASSIGNED) {
            throw new RuntimeException("Cannot mark as picked up. Current status: " + delivery.getStatus());
        }

        delivery.setStatus(DeliveryStatus.PICKED_UP);
        delivery.setPickedUpAt(LocalDateTime.now());

        return deliveryRepository.save(delivery);
    }

    @Transactional
    public Delivery markAsInTransit(int deliveryId) {
        Delivery delivery = findById(deliveryId);

        if (delivery.getStatus() != DeliveryStatus.PICKED_UP) {
            throw new RuntimeException("Cannot mark as in transit. Current status: " + delivery.getStatus());
        }

        delivery.setStatus(DeliveryStatus.IN_TRANSIT);

        return deliveryRepository.save(delivery);
    }

    @Transactional
    public Delivery markAsDelivered(int deliveryId) {
        Delivery delivery = findById(deliveryId);

        if (delivery.getStatus() != DeliveryStatus.IN_TRANSIT && delivery.getStatus() != DeliveryStatus.PICKED_UP) {
            throw new RuntimeException("Cannot mark as delivered. Current status: " + delivery.getStatus());
        }

        delivery.setStatus(DeliveryStatus.DELIVERED);
        delivery.setDeliveredAt(LocalDateTime.now());

        // Update order status to DELIVERED
        Order order = delivery.getOrder();
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);

        return deliveryRepository.save(delivery);
    }

    @Transactional
    public Delivery markAsFailed(int deliveryId, String reason) {
        Delivery delivery = findById(deliveryId);

        delivery.setStatus(DeliveryStatus.FAILED);
        delivery.setDeliveryNotes(reason);

        return deliveryRepository.save(delivery);
    }

    @Transactional
    public Delivery updateDeliveryNotes(int deliveryId, String notes) {
        Delivery delivery = findById(deliveryId);
        delivery.setDeliveryNotes(notes);
        return deliveryRepository.save(delivery);
    }

    @Transactional
    public void delete(int id) {
        if (!deliveryRepository.existsById(id)) {
            throw new RuntimeException("Delivery not found with id: " + id);
        }
        deliveryRepository.deleteById(id);
    }
}
