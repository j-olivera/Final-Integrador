package com.olivera.challenge.application.usecase.user;

import com.olivera.challenge.application.port.in.user.ExpireUser;
import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;

import java.util.List;

public class ExpireUserImpl implements ExpireUser {

    private final UserRepositoryPort userRepositoryPort;
    private final TimeProvider timeProvider;

    public ExpireUserImpl(UserRepositoryPort userRepositoryPort, TimeProvider timeProvider) {
        this.userRepositoryPort = userRepositoryPort;
        this.timeProvider = timeProvider;
    }

    @Override
    public int expire() {
        //un usuario se activa por ley, ya que el scheduler esta programado asi, no debería
        //haber usuarios en estado de PENDING siendo expirados, ya que ellos no escojen la fecha de expiración
        List<User> userActivateds = userRepositoryPort.findByStatus(UserStatus.ACTIVE);
        int expired=0;
        if (userActivateds.isEmpty()) {
            return 0;
        }
        for(User userActivated : userActivateds){
            if(userActivated.getActivationExpiresAt().isBefore(timeProvider.now())) {
                userActivated.toExpire();
                userRepositoryPort.save(userActivated);//se guarda, no se elimina, la base de datos actualiza el estado mediante la ID
                expired++;
            }
            }
        return expired;
    }
}
/*
  agrego esta función aunque el enunciado no lo aclara,
  para mejorar un poco el funcionamiento, si el programa expira usuarios
  también debería expirarlos
 */