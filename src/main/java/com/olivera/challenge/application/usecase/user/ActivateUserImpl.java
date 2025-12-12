package com.olivera.challenge.application.usecase.user;

import com.olivera.challenge.application.port.in.user.ActivateUser;
import com.olivera.challenge.application.port.out.UserReporsitory;
import com.olivera.challenge.application.services.TimeProvider;

public class ActivateUserImpl implements ActivateUser {

    private final UserReporsitory userReporsitory;
    private final TimeProvider timeProvider;

    public ActivateUserImpl(UserReporsitory userReporsitory, TimeProvider timeProvider) {
        this.userReporsitory = userReporsitory;
        this.timeProvider = timeProvider;
    }

    @Override
    public void execute() {

    }
}
