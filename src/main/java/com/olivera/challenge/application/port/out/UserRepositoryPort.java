package com.olivera.challenge.application.port.out;

import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user); //se cambio a save, por la misma razon q en Order
    Optional<User> findById(Long id);
    boolean existsByEmail(String email);
    List<User> findByStatus(UserStatus status);
    List<User> findAll();
}
