package com.olivera.challenge.usecase.order;

import com.olivera.challenge.application.dto.request.CreateOrderRequest;
import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.application.usecase.order.CreateOrderImpl;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.user.UserIsNotActiveException;
import com.olivera.challenge.domain.exceptions.user.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateOrderImplTest {

    @Mock
    private OrderRepositoryPort  orderRepositoryPort;
    @Mock
    private UserRepositoryPort userRepositoryPort;
    @Mock
    private TimeProvider timeProvider;
    @InjectMocks
    private CreateOrderImpl createOrderImpl;

    @Test
        public void createOrderSuccessTest(){
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(new BigDecimal(122));
        User user = User.createUser("juan@gmai.com","12345677",LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        user.toActivate();
        when(orderRepositoryPort.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));
        //
        OrderResponse response = createOrderImpl.createOrder(createOrderRequest,1L);
        //
        Assertions.assertEquals(new BigDecimal(122), response.getAmount());
        verify(orderRepositoryPort,times(1)).save(any(Order.class));
        verify(userRepositoryPort,times(1)).findById(1L);

    }
    @Test
    void userIsNotActiveTest(){
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(new BigDecimal(122));
        LocalDateTime now = LocalDateTime.of(2025,4,10,1,1);
        when(timeProvider.now()).thenReturn(now);
        User user = User.createUser("juan@gmai.com","12345677",now, now.plusDays(7));
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));
        //
        Assertions.assertThrows(UserIsNotActiveException.class,() -> createOrderImpl.createOrder(createOrderRequest,1L));
        //
        verify(userRepositoryPort,times(1)).findById(1L);
        verify(orderRepositoryPort,never()).save(any(Order.class));
    }
    @Test
    void userNotFoundTest(){
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(new BigDecimal(122));
        when(userRepositoryPort.findById(1L)).thenReturn(Optional.empty());
        //
        Assertions.assertThrows(UserNotFoundException.class,() -> createOrderImpl.createOrder(createOrderRequest,1L));
        //
        verify(userRepositoryPort,times(1)).findById(1L);
        verify(orderRepositoryPort,never()).save(any(Order.class));
    }

}
