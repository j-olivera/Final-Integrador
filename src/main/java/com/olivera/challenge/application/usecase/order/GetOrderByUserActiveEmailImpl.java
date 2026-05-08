package com.olivera.challenge.application.usecase.order;

import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.mappers.order.OrderMapper;
import com.olivera.challenge.application.port.in.order.GetOrdersByUserActiveEmail;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.user.UserIsExpiredException;
import com.olivera.challenge.domain.exceptions.user.UserNotFoundException;

import java.util.List;

public class GetOrderByUserActiveEmailImpl implements GetOrdersByUserActiveEmail {

    private final OrderRepositoryPort orderRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    public GetOrderByUserActiveEmailImpl(OrderRepositoryPort orderRepositoryPort, UserRepositoryPort userRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public List<OrderResponse> execute(String email) {
        //busco el user por su email
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        //logica brusca
        if(!user.getStatus().equals(UserStatus.ACTIVE)){
            throw new UserIsExpiredException("User is not ACTIVE");
        }
        return orderRepositoryPort.findByUserEmail(email)
                .stream()
                .map(OrderMapper::toOrderResponse)
                .toList();
    }
}
