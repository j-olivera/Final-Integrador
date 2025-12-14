package com.olivera.challenge.application.port.out;

import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.enums.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order); //se le cambio el nombre, asi se entiende que sirve como creacion y/o actualización
    Optional<Order> findById(Long id);
    List<Order> findByStatus(OrderStatus status);
}
