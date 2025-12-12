package com.olivera.challenge.application.port.out;

import com.olivera.challenge.application.dto.response.UserResponse;
import com.olivera.challenge.domain.entities.User;

public interface UserReporsitoryPort {
    UserResponse registerUser(User user);
    UserResponse findById(Long id);
    void activateUser();
}
