package com.olivera.challenge.domain;

import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.exceptions.InvalidDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UserEntityTest {

    @Test
    public void constructorTest() {
        String email = "juanswc@gmail.com";
        String password = "juanswc1232";
        LocalDateTime expires = LocalDateTime.now().plusDays(7);
        LocalDateTime created = LocalDateTime.now();

        User user = User.createUser(email,password,created,expires);

        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getEmail());
        Assertions.assertNotNull(user.getPassword());
    }

    @Test
    void InvalidDataExceptionTest() {
        String email = " ";
        String password = "juanswc1232";
        LocalDateTime expires = LocalDateTime.now().plusDays(7);
        LocalDateTime created = LocalDateTime.now();

        Assertions.assertThrows(InvalidDataException.class, () -> User.createUser(email,password,created,expires));
    }
}
