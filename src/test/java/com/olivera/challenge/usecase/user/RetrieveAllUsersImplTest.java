package com.olivera.challenge.usecase.user;

import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.application.usecase.user.RetrieveAllUsersImpl;
import com.olivera.challenge.domain.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetrieveAllUsersImplTest {
    @Mock
    private UserRepositoryPort userRepositoryPort;
    @InjectMocks
    private RetrieveAllUsersImpl retrieveAllUsers;

    @Test
    public void retrieveAllUsersTest(){
        User user1 = mock(User.class);
        User user2 = mock(User.class);
        when(userRepositoryPort.findAll()).thenReturn(List.of(user1,user2));
        int size=retrieveAllUsers.execute().size();
        Assertions.assertEquals(2,size);
        verify(userRepositoryPort,times(1)).findAll();
    }
}
