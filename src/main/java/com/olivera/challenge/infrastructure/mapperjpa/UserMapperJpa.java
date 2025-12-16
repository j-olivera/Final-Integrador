package com.olivera.challenge.infrastructure.mapperjpa;

import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapperJpa {
    public UserEntity toEntity(User user){
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
    public User toDomain(UserEntity userEntity){
        User user = User.reconstructUser(userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getStatus(),
                userEntity.getActivationCode(),
                userEntity.getActivationExpiresAt(),
                userEntity.getCreatedAt());
        return user;
    }
}
