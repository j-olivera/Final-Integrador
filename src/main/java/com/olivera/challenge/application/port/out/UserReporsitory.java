package com.olivera.challenge.application.port.out;

import com.olivera.challenge.application.dto.response.UserResponse;
import com.olivera.challenge.domain.entities.User;

import java.util.Optional;

public interface UserReporsitory {
    User registerUser(User user);
    Optional<User> findById(Long id);
    void activateUser();
    boolean existsByEmail(String email);
}
