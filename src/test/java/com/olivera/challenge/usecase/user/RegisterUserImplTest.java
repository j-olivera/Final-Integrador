package com.olivera.challenge.usecase.user;

import com.olivera.challenge.application.dto.request.CreateUserRequest;
import com.olivera.challenge.application.dto.response.UserResponse;
import com.olivera.challenge.application.mappers.user.UserMapper;
import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.application.usecase.user.RegisterUserImpl;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.exceptions.InvalidDataException;
import com.olivera.challenge.domain.exceptions.user.EmailAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterUserImplTest {
    @Mock
    UserRepositoryPort userRepositoryPort;
    @Mock
    TimeProvider timeProvider;
    @InjectMocks
    RegisterUserImpl registerUserImpl;

    @Test
    void registerUserSuccess(){
        LocalDateTime now = LocalDateTime.of(2025,4,1,10,0,0);
        CreateUserRequest request = new CreateUserRequest("juanswc@gmail.com", "12314514");

        when(timeProvider.now()).thenReturn(now);
        when(userRepositoryPort.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepositoryPort.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        //
        UserResponse userResponse = registerUserImpl.register(request);
        //
        Assertions.assertNotNull(userResponse);

        Assertions.assertEquals("juanswc@gmail.com", userResponse.getEmail());
        verify(userRepositoryPort).save(any(User.class));
        verify(userRepositoryPort).existsByEmail(request.getEmail());
    }
    @Test
    void emailAlreadyExists(){
        CreateUserRequest request = new CreateUserRequest("juanswc@gmail.com", "12314514");

        when(userRepositoryPort.existsByEmail(request.getEmail())).thenReturn(true);

        Assertions.assertThrows(EmailAlreadyExistsException.class, ()-> registerUserImpl.register(request));

        verify(userRepositoryPort,never()).save(any(User.class));

    }

    @Test
    void invalidDataCannotRegister(){
        CreateUserRequest request = new CreateUserRequest("gmail.com", "12314514");

        Assertions.assertThrows(InvalidDataException.class, ()-> registerUserImpl.register(request));

        verify(userRepositoryPort,never()).save(any(User.class));
    }
}
