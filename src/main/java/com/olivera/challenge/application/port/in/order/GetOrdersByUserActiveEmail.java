package com.olivera.challenge.application.port.in.order;

import com.olivera.challenge.application.dto.response.OrderResponse;

import java.util.List;

public interface GetOrdersByUserActiveEmail {
    List<OrderResponse> execute(String email);
}
