package net.safedata.microservices.training.order.outbound.adapter;

import net.safedata.microservices.training.order.domain.model.Order;
import net.safedata.microservices.training.order.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersistenceOutboundAdapterTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PersistenceOutboundAdapter persistenceOutboundAdapter;

    @Test
    void findById_shouldReturnEmptyOptional_whenOrderDoesNotExist() {
        // Given
        long nonExistentOrderId = 999L;
        when(orderRepository.findById(nonExistentOrderId)).thenReturn(Optional.empty());

        // When
        Optional<Order> result = persistenceOutboundAdapter.findById(nonExistentOrderId);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void findById_shouldReturnOrder_whenOrderExists() {
        // Given
        long existingOrderId = 1L;
        Order expectedOrder = new Order(1L, 100.0);
        when(orderRepository.findById(existingOrderId)).thenReturn(Optional.of(expectedOrder));

        // When
        Optional<Order> result = persistenceOutboundAdapter.findById(existingOrderId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedOrder);
    }
}
