package com.olivera.challenge.application.usecase.user;

import com.olivera.challenge.application.port.in.user.ExpireUser;
import com.olivera.challenge.application.port.out.UserReporsitoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;

import java.util.List;

public class ExpireUserImpl implements ExpireUser {

    private final UserReporsitoryPort userReporsitoryPort;
    private final TimeProvider timeProvider;

    public ExpireUserImpl(UserReporsitoryPort userReporsitoryPort, TimeProvider timeProvider) {
        this.userReporsitoryPort = userReporsitoryPort;
        this.timeProvider = timeProvider;
    }

    @Override
    public void expire() {
        //un usuario se activa por ley, ya que el scheduler esta programado asi, no debería
        //haber usuarios en estado de PENDING siendo expirados, ya que ellos no escojen la fecha de expiración
        List<User> userActivateds = userReporsitoryPort.findByStatus(UserStatus.ACTIVE);
        for(User userActivated : userActivateds){
            userActivated.toExpire();
            userReporsitoryPort.save(userActivated);//se guarda, no se elimina, la base de datos actualiza el estado mediante la ID
        }
    }
}
/*
  agrego esta función aunque el enunciado no lo aclara,
  para mejorar un poco el funcionamiento, si el programa expira usuarios
  también debería expirarlos
 */