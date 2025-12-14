package com.olivera.challenge.infrastructure.adapter.user;

import com.olivera.challenge.application.port.out.UserReporsitoryPort;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.user.UserNotFoundException;
import com.olivera.challenge.infrastructure.entity.UserEntity;
import com.olivera.challenge.infrastructure.mapperjpa.UserMapperJpa;
import com.olivera.challenge.infrastructure.repository.UserRepositoryJpa;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class UserRepositoryAdapter implements UserReporsitoryPort {

    private final UserMapperJpa userMapperJpa;
    private final UserRepositoryJpa userRepositoryJpa;

    public UserRepositoryAdapter(UserMapperJpa userMapperJpa, UserRepositoryJpa userRepositoryJpa) {
        this.userMapperJpa = userMapperJpa;
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapperJpa.toEntity(user);
        UserEntity savedUser = userRepositoryJpa.save(userEntity);
        return userMapperJpa.toDomain(savedUser);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepositoryJpa.findById(id)
                .map(userMapperJpa::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepositoryJpa.existsByEmail(email);
    }

    @Override
    public List<User> findByStatus(UserStatus status) {
        return userRepositoryJpa.findByStatus(status)
                .stream().map(userMapperJpa::toDomain)
                .collect(Collectors.toList());
    }
}
