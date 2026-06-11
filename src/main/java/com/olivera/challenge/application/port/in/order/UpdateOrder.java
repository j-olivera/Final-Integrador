package com.olivera.challenge.application.port.in.order;

import com.olivera.challenge.application.dto.request.UpdateOrderRequest;
import com.olivera.challenge.application.dto.response.OrderResponse;

public interface UpdateOrder {
    OrderResponse execute(Long id, UpdateOrderRequest request, String email);
}
