package com.olivera.challenge.application.usecase.user;

import com.olivera.challenge.application.port.in.user.RetrieveAllUsers;
import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.domain.entities.User;

import java.util.List;

public class RetrieveAllUsersImpl implements RetrieveAllUsers {//para generar los csv

    private final UserRepositoryPort userRepositoryPort;

    public RetrieveAllUsersImpl(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public List<User> execute() {
        return userRepositoryPort.findAll();
    }
}

