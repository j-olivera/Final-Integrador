package com.olivera.challenge.application.port.in.user;

import com.olivera.challenge.domain.entities.User;

public interface RegisterUser {
    public User register(User user); //temp -> cambiar a request y response
}
