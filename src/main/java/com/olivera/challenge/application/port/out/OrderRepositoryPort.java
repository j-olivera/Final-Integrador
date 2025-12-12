package com.olivera.challenge.application.port.out;

import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.enums.order.OrderStatus;

import java.util.List;

public interface OrderRepositoryPort {
    Order createOrder(Order order);
    Order findById(Long id);
    void processPendingOrder();
    List<Order> findByStatus(OrderStatus status);
    Order save (Order order);
}
