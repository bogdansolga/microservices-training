package net.safedata.spring.training.complete.project.service;

import net.safedata.spring.training.complete.project.dto.CreateOrderRequest;
import net.safedata.spring.training.complete.project.dto.OrderDTO;
import net.safedata.spring.training.complete.project.dto.OrderItemRequest;
import net.safedata.spring.training.complete.project.model.Customer;
import net.safedata.spring.training.complete.project.model.MenuItem;
import net.safedata.spring.training.complete.project.model.Order;
import net.safedata.spring.training.complete.project.model.OrderItem;
import net.safedata.spring.training.complete.project.model.OrderStatus;
import net.safedata.spring.training.complete.project.model.Payment;
import net.safedata.spring.training.complete.project.model.Restaurant;
import net.safedata.spring.training.complete.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                       CustomerService customerService,
                       RestaurantService restaurantService,
                       MenuItemService menuItemService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
    }

    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request) {
        // Validate customer and restaurant
        Customer customer = customerService.findById(request.customerId());
        Restaurant restaurant = restaurantService.findById(request.restaurantId());

        // Create order
        Order order = new Order(customer, restaurant);

        // Create order items
        Set<OrderItem> orderItems = new HashSet<>();
        double totalAmount = 0.0;

        for (OrderItemRequest itemRequest : request.items()) {
            MenuItem menuItem = menuItemService.findById(itemRequest.menuItemId());
            OrderItem orderItem = new OrderItem(order, menuItem, itemRequest.quantity());
            orderItems.add(orderItem);
            totalAmount += orderItem.getSubtotal();
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        // Create payment
        Payment payment = new Payment(order, request.paymentMethod(), totalAmount);
        order.setPayment(payment);

        // Save order (cascades to items and payment)
        Order savedOrder = orderRepository.save(order);

        return toDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return toDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findByCustomerId(int customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO updateStatus(int orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return toDTO(updatedOrder);
    }

    @Transactional
    public void delete(int id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO toDTO(Order order) {
        List<OrderDTO.OrderItemDTO> itemDTOs = order.getOrderItems().stream()
                .map(item -> new OrderDTO.OrderItemDTO(
                        item.getMenuItem().getId(),
                        item.getMenuItem().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getRestaurant().getId(),
                order.getRestaurant().getName(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotalAmount(),
                itemDTOs,
                order.getPayment() != null ? order.getPayment().getPaymentMethod() : null,
                order.getPayment() != null ? order.getPayment().getPaymentStatus() : null
        );
    }
}
