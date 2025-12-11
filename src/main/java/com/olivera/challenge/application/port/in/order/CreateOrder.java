package com.olivera.challenge.application.port.in.order;

import com.olivera.challenge.domain.entities.Order;

public interface CreateOrder {
    public Order createOrder(Order order); //temp -> cambiar a request y response
}
