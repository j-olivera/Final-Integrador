package com.olivera.challenge.application.port.out;

import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.domain.entities.Order;

public interface OrderRepositoryPort {
    Order createOrder(Order order);
    Order findById(Long id);
    void processPendingOrder();
}
