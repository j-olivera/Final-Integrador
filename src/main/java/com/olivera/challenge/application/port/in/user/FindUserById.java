package com.olivera.challenge.application.port.in.user;

import com.olivera.challenge.application.dto.response.UserResponse;
import com.olivera.challenge.domain.entities.User;

public interface FindUserById {
    UserResponse findById(Long id);
}
