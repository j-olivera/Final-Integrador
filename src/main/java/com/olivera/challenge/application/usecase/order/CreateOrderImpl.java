package com.olivera.challenge.application.usecase.order;

import com.olivera.challenge.application.dto.request.CreateOrderRequest;
import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.mappers.order.OrderMapper;
import com.olivera.challenge.application.port.in.order.CreateOrder;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.port.out.UserReporsitoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.exceptions.user.UserIsNotActiveException;
import com.olivera.challenge.domain.exceptions.user.UserNotFoundException;

public class CreateOrderImpl implements CreateOrder {

    private final OrderRepositoryPort orderRepositoryPort;
    private final UserReporsitoryPort userReporsitory;
    private final TimeProvider timeProvider;

    public CreateOrderImpl(OrderRepositoryPort orderRepositoryPort, UserReporsitoryPort userReporsitory, TimeProvider timeProvider) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.userReporsitory = userReporsitory;
        this.timeProvider = timeProvider;
    }

    public OrderResponse createOrder(CreateOrderRequest createOrderRequest, Long userId){
        createOrderRequest.validar();
        User user = userReporsitory.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));
        if(!user.isActive()){
            throw new UserIsNotActiveException("Only user's with status ACTIVE can be create orders");
        }
        Order order = Order.createOrder(user, createOrderRequest.getAmount(),timeProvider.now());
        Order save = orderRepositoryPort.createOrder(order);
        return OrderMapper.toOrderResponse(save);
    }
}
