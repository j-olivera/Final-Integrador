package com.olivera.challenge.application.usecase.user;

import com.olivera.challenge.application.dto.request.CreateUserRequest;
import com.olivera.challenge.application.dto.response.UserResponse;
import com.olivera.challenge.application.mappers.user.UserMapper;
import com.olivera.challenge.application.port.in.user.RegisterUser;
import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.exceptions.user.EmailAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class RegisterUserImpl implements RegisterUser {

    private final UserRepositoryPort userReporsitory;
    private final TimeProvider timeProvider;
    private final PasswordEncoder passwordEncoder; // BCrypt para hashear la contraseña al guardar

    public RegisterUserImpl(UserRepositoryPort userReporsitory, TimeProvider timeProvider, PasswordEncoder passwordEncoder) {
        this.userReporsitory = userReporsitory;
        this.timeProvider = timeProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse register(CreateUserRequest request) {
        request.validar();
        if(userReporsitory.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        LocalDateTime now = timeProvider.now();
        LocalDateTime expires = now.plusDays(7);
        // Hashear la contraseña con BCrypt antes de persistir (el login usa BCrypt para comparar)
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.createUser(request.getEmail(), hashedPassword, now, expires);
        User save = userReporsitory.save(user);
        return UserMapper.toResponse(save);
    }
}
