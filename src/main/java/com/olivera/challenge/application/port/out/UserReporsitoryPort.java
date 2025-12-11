package com.olivera.challenge.application.port.out;

import com.olivera.challenge.domain.entities.User;

public interface UserReporsitoryPort {
    User registerUser(User user);
}
