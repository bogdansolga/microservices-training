package net.safedata.microservices.training.order.service;

import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderDeliveredEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderProcessedEvent;
import net.safedata.microservices.training.order.domain.model.Order;
import net.safedata.microservices.training.order.domain.model.OrderStatus;
import net.safedata.microservices.training.order.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.order.outbound.port.PersistenceOutboundPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceEventHandlerTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private MessagingOutboundPort messagingOutboundPort;

    @Mock
    private PersistenceOutboundPort persistenceOutboundPort;

    @Mock
    private ThreadPoolTaskExecutor customThreadPool;

    @InjectMocks
    private OrderService orderService;

    @Test
    void handleOrderCharged_shouldHandleGracefully_whenOrderDoesNotExist() {
        // Given
        long nonExistentOrderId = 999L;
        long customerId = 1L;
        OrderChargedEvent event = new OrderChargedEvent(1L, 1L, customerId, nonExistentOrderId);
        when(persistenceOutboundPort.findById(nonExistentOrderId)).thenReturn(Optional.empty());

        // When
        orderService.handleOrderCharged(event);

        // Then
        verify(persistenceOutboundPort).findById(nonExistentOrderId);
        verify(persistenceOutboundPort, never()).save(any(Order.class));
    }

    @Test
    void handleOrderCharged_shouldUpdateOrderStatus_whenOrderExists() {
        // Given
        long orderId = 1L;
        long customerId = 1L;
        Order order = new Order(customerId, 100.0);
        order.setStatus(OrderStatus.CREATED);
        OrderChargedEvent event = new OrderChargedEvent(1L, 1L, customerId, orderId);
        when(persistenceOutboundPort.findById(orderId)).thenReturn(Optional.of(order));

        // When
        orderService.handleOrderCharged(event);

        // Then
        verify(persistenceOutboundPort).findById(orderId);
        verify(persistenceOutboundPort).save(order);
        assert order.getStatus() == OrderStatus.PAYED;
    }

    @Test
    void handleOrderNotCharged_shouldHandleGracefully_whenOrderDoesNotExist() {
        // Given
        long nonExistentOrderId = 999L;
        long customerId = 1L;
        OrderNotChargedEvent event = new OrderNotChargedEvent(1L, 1L, customerId, nonExistentOrderId, "Insufficient funds");
        when(persistenceOutboundPort.findById(nonExistentOrderId)).thenReturn(Optional.empty());

        // When
        orderService.handleOrderNotCharged(event);

        // Then
        verify(persistenceOutboundPort).findById(nonExistentOrderId);
        verify(persistenceOutboundPort, never()).save(any(Order.class));
    }

    @Test
    void handleOrderProcessed_shouldHandleGracefully_whenOrderDoesNotExist() {
        // Given
        long nonExistentOrderId = 999L;
        long customerId = 1L;
        OrderProcessedEvent event = new OrderProcessedEvent(1L, 1L, customerId, nonExistentOrderId);
        when(persistenceOutboundPort.findById(nonExistentOrderId)).thenReturn(Optional.empty());

        // When
        orderService.handleOrderProcessed(event);

        // Then
        verify(persistenceOutboundPort).findById(nonExistentOrderId);
        verify(persistenceOutboundPort, never()).save(any(Order.class));
    }

    @Test
    void handleOrderDelivered_shouldHandleGracefully_whenOrderDoesNotExist() {
        // Given
        long nonExistentOrderId = 999L;
        long customerId = 1L;
        OrderDeliveredEvent event = new OrderDeliveredEvent(1L, 1L, customerId, nonExistentOrderId);
        when(persistenceOutboundPort.findById(nonExistentOrderId)).thenReturn(Optional.empty());

        // When
        orderService.handleOrderDelivered(event);

        // Then
        verify(persistenceOutboundPort).findById(nonExistentOrderId);
        verify(persistenceOutboundPort, never()).save(any(Order.class));
    }
}
