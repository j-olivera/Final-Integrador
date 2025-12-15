package com.olivera.challenge.application.usecase.user;

import com.olivera.challenge.application.port.in.user.RetrieveAllUsers;
import com.olivera.challenge.application.port.out.UserReporsitoryPort;
import com.olivera.challenge.domain.entities.User;

import java.util.List;

public class RetrieveAllUsersImpl implements RetrieveAllUsers {

    private final UserReporsitoryPort  userReporsitoryPort;

    public RetrieveAllUsersImpl(UserReporsitoryPort userReporsitoryPort) {
        this.userReporsitoryPort = userReporsitoryPort;
    }

    @Override
    public List<User> execute() {
        return userReporsitoryPort.findAll();
    }
}

