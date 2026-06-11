package com.olivera.challenge.application.usecase.order;

import com.olivera.challenge.application.dto.request.UpdateOrderRequest;
import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.mappers.order.OrderMapper;
import com.olivera.challenge.application.port.in.order.UpdateOrder;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.exceptions.order.OrderForbiddenException;
import com.olivera.challenge.domain.exceptions.order.OrderNotFoundException;

public class UpdateOrderImpl implements UpdateOrder {

    private final OrderRepositoryPort orderRepositoryPort;
    private final TimeProvider timeProvider;

    public UpdateOrderImpl(OrderRepositoryPort orderRepositoryPort, TimeProvider timeProvider) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.timeProvider = timeProvider;
    }

    @Override
    public OrderResponse execute(Long id, UpdateOrderRequest request, String email) {
        request.validate();
        Order order = orderRepositoryPort.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        if (!order.getUser().getEmail().equals(email)) {
            throw new OrderForbiddenException("You do not have permission to modify this order");
        }

        order.updateAmount(request.getAmount(), timeProvider.now());
        Order savedOrder = orderRepositoryPort.save(order);
        return OrderMapper.toOrderResponse(savedOrder);
    }
}
