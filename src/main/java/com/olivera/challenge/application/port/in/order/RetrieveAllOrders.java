package com.olivera.challenge.application.port.in.order;

import com.olivera.challenge.domain.entities.Order;

import java.util.List;

public interface RetrieveAllOrders {
    List<Order> execute();
}
