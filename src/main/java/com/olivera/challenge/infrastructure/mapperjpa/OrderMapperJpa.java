package com.olivera.challenge.infrastructure.mapperjpa;


import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.infrastructure.entity.OrderEntity;
import com.olivera.challenge.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperJpa {

    private final UserMapperJpa userMapperJpa;

    public OrderMapperJpa(UserMapperJpa userMapperJpa) {
        this.userMapperJpa = userMapperJpa;
    }

    public OrderEntity toEntity(Order order) {
        UserEntity user = userMapperJpa.toEntity(order.getUser());
        return new OrderEntity(
                order.getId(),
                user,
                order.getStatus(),
                order.getAmount(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
    public Order toDomain(OrderEntity entity) {
        User user = userMapperJpa.toDomain(entity.getUser());
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
