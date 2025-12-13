package com.olivera.challenge.application.port.out;

import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserReporsitoryPort {
    User registerUser(User user);
    Optional<User> findById(Long id);
    void activateUser();
    boolean existsByEmail(String email);
    List<User> findByStatus(UserStatus status);
}
