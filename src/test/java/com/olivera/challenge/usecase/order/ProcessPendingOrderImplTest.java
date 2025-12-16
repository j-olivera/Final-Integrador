package com.olivera.challenge.usecase.order;

import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.application.usecase.order.ProcessPendingOrderImpl;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.order.OrderStatus;
import com.olivera.challenge.domain.enums.user.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProcessPendingOrderImplTest {

    @Mock
    OrderRepositoryPort orderRepositoryPort;

    @Mock
    TimeProvider timeProvider;

    @InjectMocks
    ProcessPendingOrderImpl processPendingOrderImpl;

    @Test
    void processPendingOrderImplSuccessTest() {
        LocalDateTime now = LocalDateTime.now();
        when(timeProvider.now()).thenReturn(now);

        User user = User.createUser("juan@gmai.com","12345677",LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        user.toActivate(); //usuario activo
        Order pendingOrder = Order.createOrder(user,new BigDecimal(122),now);
        Order processingOrder = Order.createOrder(user,new BigDecimal(122),now);
        processingOrder.procces(now);
        when(orderRepositoryPort.findByStatus(OrderStatus.PENDING)).thenReturn(List.of(pendingOrder)); //1
        when(orderRepositoryPort.findByStatus(OrderStatus.PROCESSING)).thenReturn(List.of(processingOrder));//1

        List<Integer> result = processPendingOrderImpl.execute();

        verify(orderRepositoryPort, times(2)).save(any(Order.class));//se guardo cuando paso a proccesing y en approve

        Assertions.assertEquals(1, result.get(0));//pending
        Assertions.assertEquals(1, result.get(1));//proccess
        Assertions.assertEquals(1, result.get(2));//approve
        Assertions.assertEquals(0, result.get(3));//rejected
    }

    @Test
    void pendingToCancelledOrdersTest() {
        LocalDateTime now = LocalDateTime.now();
        when(timeProvider.now()).thenReturn(now);

        User user = createUser(UserStatus.EXPIRED);
        Order pendingOrder= createOrder(1L, user,OrderStatus.PENDING);

        when(orderRepositoryPort.findByStatus(OrderStatus.PENDING)).thenReturn(List.of(pendingOrder));
        when(orderRepositoryPort.findByStatus(OrderStatus.PROCESSING)).thenReturn(List.of());

        List<Integer> result = processPendingOrderImpl.execute();

        verify(orderRepositoryPort, times(1)).save(pendingOrder);

        Assertions.assertEquals(1, result.get(0));
        Assertions.assertEquals(0, result.get(1));
        Assertions.assertEquals(0, result.get(2));
        Assertions.assertEquals(1, result.get(3));
    }
    /*
        nota: utilize un reconstructor para simular el momento donde el usuario tenga
        una orden pendiente y ocurra el scheduler de ExpireUser, de la forma normal
        no me dejaba por la validación que hay en createOrder
        me ayudo la IA
     */
    private User createUser(UserStatus status) {
        return User.reconstructUser(
                1L,
                "juan@test.com",
                "1231414",
                status,
                "code",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now()
        );
    }

    private Order createOrder(Long id, User user, OrderStatus status) {
        return Order.reconstructOrder(
                id,
                user,
                status,
                new BigDecimal(412),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

}