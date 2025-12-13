package com.olivera.challenge.infrastructure.mapperjpa;

import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.infrastructure.entity.UserEntity;

public class UserMapperJpa {
    public static UserEntity toEntity(User user){
        return new UserEntity(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus(),
                user.getActivationCode(),
                user.getActivationExpiresAt(),
                user.getCreatedAt()
        );
    }
    public static User toDomain(UserEntity userEntity){
        User user = User.reconstructUser(userEntity.getId(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getStatus(),
                userEntity.getActivationCode(),
                userEntity.getActivationExpiresAt(),
                userEntity.getCreatedAt());
        return user;
    }
}
