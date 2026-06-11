package com.olivera.challenge.usecase.order;

import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.application.usecase.order.CancelOrderImpl;
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
public class CancelOrderImplTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;
    @Mock
    private TimeProvider timeProvider;
    @InjectMocks
    private CancelOrderImpl cancelOrderImpl;

    @Test
    void cancelOrderSuccessTest() {
        String email = "owner@gmail.com";
        LocalDateTime now = LocalDateTime.now();
        
        User user = User.reconstructUser(1L, email, "pass", UserStatus.ACTIVE, "code", now, now);
        Order order = Order.reconstructOrder(1L, user, OrderStatus.PENDING, new BigDecimal("100.00"), now, now);
        
        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.of(order));
        when(timeProvider.now()).thenReturn(now.plusMinutes(1));
        when(orderRepositoryPort.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        
        OrderResponse response = cancelOrderImpl.execute(1L, email);
        
        Assertions.assertEquals(OrderStatus.CANCELLED, response.getOrderStatus());
        verify(orderRepositoryPort).save(any(Order.class));
    }

    @Test
    void cancelOrderForbiddenTest() {
        String ownerEmail = "owner@gmail.com";
        String attackerEmail = "attacker@gmail.com";
        LocalDateTime now = LocalDateTime.now();
        
        User owner = User.reconstructUser(1L, ownerEmail, "pass", UserStatus.ACTIVE, "code", now, now);
        Order order = Order.reconstructOrder(1L, owner, OrderStatus.PENDING, new BigDecimal("100.00"), now, now);
        
        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.of(order));
        
        Assertions.assertThrows(OrderForbiddenException.class, () -> cancelOrderImpl.execute(1L, attackerEmail));
        verify(orderRepositoryPort, never()).save(any());
    }
}
