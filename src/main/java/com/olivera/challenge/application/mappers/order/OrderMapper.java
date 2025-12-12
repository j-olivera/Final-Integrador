package com.olivera.challenge.application.mappers.order;

import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.domain.entities.Order;

public class OrderMapper {

    public static OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getEmail(),
                order.getAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }

}
