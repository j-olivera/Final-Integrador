package com.olivera.challenge.usecase.user;

import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.application.usecase.user.ExpireUserImpl;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpireUserImplTest {
    @Mock
    private UserRepositoryPort userRepositoryPort;
    @Mock
    private TimeProvider timeProvider;
    @InjectMocks
    private ExpireUserImpl expireUserImpl;

    @Test
    void expireUserSuccess() {
        LocalDateTime now = LocalDateTime.of(2025,4,1,1,1,1);

        User user = mock(User.class);//objeto user sin logica
        //when(user.getEmail()).thenReturn("juan@gmail.com");
        when(user.getActivationExpiresAt()).thenReturn(now.minusDays(7));
//        when(user.getStatus()).thenReturn(UserStatus.ACTIVE);

        when(userRepositoryPort.findByStatus(UserStatus.ACTIVE)).thenReturn(List.of(user));
  //      when(userRepositoryPort.existsByEmail(user.getEmail())).thenReturn(true);
        when(timeProvider.now()).thenReturn(now);

        int cant=expireUserImpl.expire();

        Assertions.assertEquals(1,cant);
        verify(user).toExpire();
        verify(userRepositoryPort,times(1)).save(user);

    }
}
