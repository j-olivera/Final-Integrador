package com.olivera.challenge.application.port.out;

import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.domain.entities.Order;

public interface OrderRepositoryPort {
    OrderResponse createOrder(Order order);
    OrderResponse findById(Long id);
}
