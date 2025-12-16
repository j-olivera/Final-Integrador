package com.olivera.challenge.domain;

import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.exceptions.InvalidDataException;
import com.olivera.challenge.domain.exceptions.user.InvalidUserStatusException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderEntityTest {
    @Test
    void constructorTest() {
        //
        String email = "juanswc@gmail.com";
        String password = "juanswc1232";
        LocalDateTime expires = LocalDateTime.now().plusDays(7);
        LocalDateTime created = LocalDateTime.now();
        User user = User.createUser(email,password,created,expires);
        //
        BigDecimal amount = new BigDecimal(500);
        user.toActivate();
        Order order = Order.createOrder(user,amount,LocalDateTime.now());
        //
        Assertions.assertNotNull(order);
        Assertions.assertNotNull(order.getUser());
        Assertions.assertNotNull(order.getAmount());
    }

    @Test
    void invalidDataExceptionTest(){
        String email = "juanswc@gmail.com";
        String password = "juanswc1232";
        LocalDateTime expires = LocalDateTime.now().plusDays(7);
        LocalDateTime created = LocalDateTime.now();
        User user = User.createUser(email,password,created,expires);
        //
        user.toActivate();
        //
        Assertions.assertThrows(InvalidDataException.class, ()-> Order.createOrder(user,null,LocalDateTime.now()));
    }

    @Test
    void userIsNotActiveExceptionTest(){
        String email = "juanswc@gmail.com";
        String password = "juanswc1232";
        LocalDateTime expires = LocalDateTime.now().plusDays(7);
        LocalDateTime created = LocalDateTime.now();
        User user = User.createUser(email,password,created,expires);
        //
        //
        Assertions.assertThrows(InvalidUserStatusException.class, ()-> Order.createOrder(user,new BigDecimal(443),LocalDateTime.now()));
    }
}
