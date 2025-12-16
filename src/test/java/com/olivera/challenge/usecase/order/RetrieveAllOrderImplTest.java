package com.olivera.challenge.usecase.order;

import com.olivera.challenge.application.port.in.order.RetrieveAllOrders;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.usecase.order.RetrieveAllOrdersImpl;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RetrieveAllOrderImplTest {
    @Mock
    private OrderRepositoryPort orderRepositoryJpa;
    @InjectMocks
    private RetrieveAllOrdersImpl retrieveAllOrders;

    @Test
    void retrieveAllOrders(){
        User user = User.createUser("juan@gmai.com","12345677", LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        user.toActivate();
        Order order = Order.createOrder(user, BigDecimal.TEN, LocalDateTime.now().plusDays(7));
        Order order2 = Order.createOrder(user, BigDecimal.TEN, LocalDateTime.now().plusDays(7));
        //
        when(orderRepositoryJpa.findAll()).thenReturn(List.of(order,order2));
        //
        List<Order> list = retrieveAllOrders.execute();
        //
        Assertions.assertEquals(2,list.size());
    }
}
