package com.olivera.challenge.application.port.in.order;

import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.domain.entities.Order;

import java.util.List;

public interface RetrieveAllOrders {
    List<OrderResponse> execute();
}
