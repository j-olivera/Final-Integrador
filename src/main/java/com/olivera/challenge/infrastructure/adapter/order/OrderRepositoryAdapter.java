package com.olivera.challenge.infrastructure.adapter.order;

import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.enums.order.OrderStatus;

import java.util.List;

public class OrderRepositoryAdapter implements OrderRepositoryPort {



    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public void processPendingOrder() {

    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return List.of();
    }

    @Override
    public Order save(Order order) {
        return null;
    }
}
