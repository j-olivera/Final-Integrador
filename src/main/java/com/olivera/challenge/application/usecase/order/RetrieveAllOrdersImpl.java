package com.olivera.challenge.application.usecase.order;

import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.mappers.order.OrderMapper;
import com.olivera.challenge.application.port.in.order.RetrieveAllOrders;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.domain.entities.Order;

import java.util.List;

public class RetrieveAllOrdersImpl implements RetrieveAllOrders { //para generar los csv

    private final OrderRepositoryPort orderRepositoryJpa;

    public RetrieveAllOrdersImpl(OrderRepositoryPort orderRepositoryJpa) {
        this.orderRepositoryJpa = orderRepositoryJpa;
    }

    @Override
    public List<OrderResponse> execute() {
        return orderRepositoryJpa.findAll().
                stream()
                .map(OrderMapper::toOrderResponse)
                .toList();
    }
}
// nuevo use case que traiga las ordenes de un usuario por su email