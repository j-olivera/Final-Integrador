package com.olivera.challenge.application.port.in.order;

import com.olivera.challenge.application.dto.request.CreateOrderRequest;
import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.domain.entities.Order;

public interface CreateOrder {
     OrderResponse createOrder(CreateOrderRequest amount, Long userId); //temp -> cambiar a request y response
}
