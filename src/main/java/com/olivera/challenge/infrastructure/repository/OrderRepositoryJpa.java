package com.olivera.challenge.infrastructure.repository;

import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.enums.order.OrderStatus;
import com.olivera.challenge.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepositoryJpa extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(OrderStatus status);
}
