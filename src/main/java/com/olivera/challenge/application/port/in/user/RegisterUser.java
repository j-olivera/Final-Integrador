package com.olivera.challenge.application.port.in.user;

import com.olivera.challenge.application.dto.request.CreateUserRequest;
import com.olivera.challenge.application.dto.response.UserResponse;
import com.olivera.challenge.domain.entities.User;

public interface RegisterUser {
    public UserResponse register(CreateUserRequest request); //temp -> cambiar a request y response
}
