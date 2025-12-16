package com.olivera.challenge.usecase.user;

import com.olivera.challenge.application.dto.request.CreateUserRequest;
import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.application.usecase.user.ActivateUserImpl;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.user.UserIsExpiredException;
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
public class ActivateUserImplTest {
    @Mock
    private UserRepositoryPort  userRepositoryPort;
    @Mock
    private TimeProvider timeProvider;
    @InjectMocks
    private ActivateUserImpl activateUserImpl;

    @Test
    void activateUserSuccess(){
        LocalDateTime now = LocalDateTime.of(2025,4,1,1,1,1);

        User user = mock(User.class);//objeto user sin logica
        when(user.getEmail()).thenReturn("juan@gmail.com");
        when(user.getActivationExpiresAt()).thenReturn(now.plusDays(7));
        //when(user.isPending()).thenReturn(true);

        when(userRepositoryPort.findByStatus(UserStatus.PENDING)).thenReturn(List.of(user));//1
        when(userRepositoryPort.existsByEmail(user.getEmail())).thenReturn(true);
        when(timeProvider.now()).thenReturn(now);

        int cant = activateUserImpl.execute();

        Assertions.assertEquals(1,cant);
        verify(user).toActivate();//el usuario fue activado
        verify(userRepositoryPort).save(user);//el usuario fue guardado
        //solo para verificar que el metodo se llamo
    }

    @Test
    void noPendingUsersReturn(){
        when(userRepositoryPort.findByStatus(UserStatus.PENDING)).thenReturn(List.of());//0
        int cant =  activateUserImpl.execute();
        Assertions.assertEquals(0,cant);
        verify(userRepositoryPort,never()).save(any(User.class));
    }

    @Test
    void userExpiredException(){
        LocalDateTime now = LocalDateTime.of(2025,4,1,1,1,1);

        User user = mock(User.class);//objeto user sin logica
        when(user.getEmail()).thenReturn("juan@gmail.com");
        when(user.getActivationExpiresAt()).thenReturn(now.minusMinutes(1));

        when(userRepositoryPort.findByStatus(UserStatus.PENDING)).thenReturn(List.of(user));
        when(userRepositoryPort.existsByEmail(user.getEmail())).thenReturn(true);
        when(timeProvider.now()).thenReturn(now);

        Assertions.assertThrows(UserIsExpiredException.class,() -> activateUserImpl.execute());
    }
}
