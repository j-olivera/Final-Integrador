package com.olivera.challenge.infrastructure.mapperjpa;


import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.infrastructure.entity.OrderEntity;
import com.olivera.challenge.infrastructure.entity.UserEntity;

public class OrderMapperJpa {

    public static OrderEntity toEntity(Order order) {
        UserEntity user = UserMapperJpa.toEntity(order.getUser());
        return new OrderEntity(
                order.getId(),
                user,
                order.getStatus(),
                order.getAmount(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
    public static Order toDomain(OrderEntity entity) {
        User user = UserMapperJpa.toDomain(entity.getUser());
        return Order.reconstructOrder(
                entity.getId(),
                user,
                entity.getStatus(),
                entity.getAmount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

}
