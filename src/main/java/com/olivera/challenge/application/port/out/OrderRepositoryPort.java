package com.olivera.challenge.application.port.out;

import com.olivera.challenge.domain.entities.Order;

public interface OrderRepositoryPort {
    Order createOrder(Order order);
}
