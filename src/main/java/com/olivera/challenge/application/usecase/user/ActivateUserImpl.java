package com.olivera.challenge.application.usecase.user;

import com.olivera.challenge.application.port.in.user.ActivateUser;
import com.olivera.challenge.application.port.out.UserReporsitory;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.user.UserNotFoundException;

import java.util.List;

public class ActivateUserImpl implements ActivateUser {

    private final UserReporsitory userReporsitory;

    public ActivateUserImpl(UserReporsitory userReporsitory) {
        this.userReporsitory = userReporsitory;
    }
    @Override
    public void execute() {
        List<User> userPendings = userReporsitory.findByStatus(UserStatus.PENDING);
        for (User user : userPendings) {
            if(user.isPending()){
                //comprobar si esta registrado, solo por las dudas ya que si esta en PENDING debería estar registrado
                if(!userReporsitory.existsByEmail(user.getEmail())){
                    throw new UserNotFoundException("User not found");
                }
                userReporsitory.activateUser();
            }
        }
    }
}
