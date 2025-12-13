package com.olivera.challenge.application.usecase.user;

import com.olivera.challenge.application.dto.request.CreateUserRequest;
import com.olivera.challenge.application.dto.response.UserResponse;
import com.olivera.challenge.application.mappers.user.UserMapper;
import com.olivera.challenge.application.port.in.user.RegisterUser;
import com.olivera.challenge.application.port.out.UserReporsitoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.exceptions.user.EmailAlreadyExistsException;

import java.time.LocalDateTime;

public class RegisterUserUseCase implements RegisterUser {

    private final UserReporsitoryPort userReporsitory;
    private final TimeProvider timeProvider;

    public RegisterUserUseCase(UserReporsitoryPort userReporsitory, TimeProvider timeProvider) {
        this.userReporsitory = userReporsitory;
        this.timeProvider = timeProvider;
    }

    @Override
    public UserResponse register(CreateUserRequest request) {
        request.validar();
        if(userReporsitory.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        LocalDateTime now = timeProvider.now();
        LocalDateTime expires = now.plusDays(7);
        User user = User.createUser(request.getEmail(), request.getPassword(),now,expires);
        User save = userReporsitory.registerUser(user);
        return UserMapper.toResponse(save);
    }
}
