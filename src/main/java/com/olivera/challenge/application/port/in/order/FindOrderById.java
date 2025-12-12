package com.olivera.challenge.application.port.in.order;

import com.olivera.challenge.application.dto.response.OrderResponse;

public interface FindOrderById {
    OrderResponse findById(Long id);
}
