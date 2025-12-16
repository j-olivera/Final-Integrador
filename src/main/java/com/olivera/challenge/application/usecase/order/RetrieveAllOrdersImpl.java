package com.olivera.challenge.application.usecase.order;

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
    public List<Order> execute() {
        return orderRepositoryJpa.findAll();
    }
}
