package com.olivera.challenge.application.mappers.user;

import com.olivera.challenge.application.dto.request.CreateUserRequest;
import com.olivera.challenge.application.dto.response.UserResponse;
import com.olivera.challenge.domain.entities.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getStatus(),
                user.getActivationExpiresAt()
        );
    }


}
