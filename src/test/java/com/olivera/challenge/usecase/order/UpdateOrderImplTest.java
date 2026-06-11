package com.olivera.challenge.usecase.order;

import com.olivera.challenge.application.dto.request.UpdateOrderRequest;
import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.application.usecase.order.UpdateOrderImpl;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.order.OrderStatus;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.order.OrderForbiddenException;
import com.olivera.challenge.domain.exceptions.order.OrderNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateOrderImplTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;
    @Mock
    private TimeProvider timeProvider;
    @InjectMocks
    private UpdateOrderImpl updateOrderImpl;

    @Test
    void updateOrderSuccessTest() {
        String email = "owner@gmail.com";
        BigDecimal oldAmount = new BigDecimal("100.00");
        BigDecimal newAmount = new BigDecimal("150.00");
        LocalDateTime now = LocalDateTime.now();
        
        User user = User.reconstructUser(1L, email, "pass", UserStatus.ACTIVE, "code", now, now);
        Order order = Order.reconstructOrder(1L, user, OrderStatus.PENDING, oldAmount, now, now);
        
        UpdateOrderRequest request = new UpdateOrderRequest(newAmount);
        
        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.of(order));
        when(timeProvider.now()).thenReturn(now.plusMinutes(1));
        when(orderRepositoryPort.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        
        OrderResponse response = updateOrderImpl.execute(1L, request, email);
        
        Assertions.assertEquals(newAmount, response.getAmount());
        verify(orderRepositoryPort).save(any(Order.class));
    }

    @Test
    void updateOrderNotFoundTest() {
        UpdateOrderRequest request = new UpdateOrderRequest(new BigDecimal("150.00"));
        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        
        Assertions.assertThrows(OrderNotFoundException.class, () -> updateOrderImpl.execute(1L, request, "any@mail.com"));
        verify(orderRepositoryPort, never()).save(any());
    }

    @Test
    void updateOrderForbiddenTest() {
        String ownerEmail = "owner@gmail.com";
        String attackerEmail = "attacker@gmail.com";
        BigDecimal amount = new BigDecimal("100.00");
        LocalDateTime now = LocalDateTime.now();
        
        User owner = User.reconstructUser(1L, ownerEmail, "pass", UserStatus.ACTIVE, "code", now, now);
        Order order = Order.reconstructOrder(1L, owner, OrderStatus.PENDING, amount, now, now);
        
        UpdateOrderRequest request = new UpdateOrderRequest(new BigDecimal("200.00"));
        
        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.of(order));
        
        Assertions.assertThrows(OrderForbiddenException.class, () -> updateOrderImpl.execute(1L, request, attackerEmail));
        verify(orderRepositoryPort, never()).save(any());
    }
}
