package com.olivera.challenge.application.usecase.user;

import com.olivera.challenge.application.port.in.user.ActivateUser;
import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.user.UserIsExpiredException;
import com.olivera.challenge.domain.exceptions.user.UserNotFoundException;

import java.util.List;

public class ActivateUserImpl implements ActivateUser {

    private final UserRepositoryPort userReporsitory;
    private final TimeProvider timeProvider;
    public ActivateUserImpl(UserRepositoryPort userReporsitory, TimeProvider timeProvider) {
        this.userReporsitory = userReporsitory;
        this.timeProvider = timeProvider;
    }
    @Override
    public int execute() {
        List<User> userPendings = userReporsitory.findByStatus(UserStatus.PENDING);
        if(userPendings.isEmpty()){
            return 0;
        }
        for (User user : userPendings) {
            //comprobar si esta registrado, solo por las dudas ya que si esta en PENDING debería estar registrado
            if(!userReporsitory.existsByEmail(user.getEmail())){
                throw new UserNotFoundException("User not found");
            }
            if(user.getActivationExpiresAt().isBefore(timeProvider.now())){
                throw new UserIsExpiredException("User can't be activated, reason: Activation expired");
            }
            user.toActivate();
            userReporsitory.save(user);

        }
        return userPendings.size();
    }
}
