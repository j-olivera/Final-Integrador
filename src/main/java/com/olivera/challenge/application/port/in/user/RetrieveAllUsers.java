package com.olivera.challenge.application.port.in.user;

import com.olivera.challenge.domain.entities.User;

import java.util.List;

public interface RetrieveAllUsers {
    List<User> execute();
}
