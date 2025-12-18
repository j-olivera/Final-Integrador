package com.olivera.challenge.infrastructure.adapter.order;

import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.enums.order.OrderStatus;
import com.olivera.challenge.infrastructure.entity.OrderEntity;
import com.olivera.challenge.infrastructure.mapperjpa.OrderMapperJpa;
import com.olivera.challenge.infrastructure.repository.OrderRepositoryJpa;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class OrderRepositoryAdapter implements OrderRepositoryPort {


    private final OrderRepositoryJpa orderRepositoryJpa;
    private final OrderMapperJpa orderMapperJpa;

    public OrderRepositoryAdapter(OrderRepositoryJpa orderRepositoryJpa, OrderMapperJpa orderMapperJpa) {
        this.orderRepositoryJpa = orderRepositoryJpa;
        this.orderMapperJpa = orderMapperJpa;
    }


    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderMapperJpa.toEntity(order);
        OrderEntity savedEntity = orderRepositoryJpa.save(orderEntity);
        return orderMapperJpa.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepositoryJpa.findById(id).map(orderMapperJpa::toDomain);
    }


    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orderRepositoryJpa.findByStatus(status)
                .stream()
                .map(orderMapperJpa::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findAll() {
        return orderRepositoryJpa.findAll()
                .stream()
                .map(orderMapperJpa::toDomain).
                collect(Collectors.toList());
    }

}
