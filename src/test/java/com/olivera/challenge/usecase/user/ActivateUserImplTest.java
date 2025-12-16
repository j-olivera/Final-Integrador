package com.olivera.challenge.usecase.user;

import com.olivera.challenge.application.dto.request.CreateUserRequest;
import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.application.usecase.user.ActivateUserImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ActivateUserImplTest {
    @Mock
    UserRepositoryPort  userRepositoryPort;
    @InjectMocks
    ActivateUserImpl activateUserImpl;

}
