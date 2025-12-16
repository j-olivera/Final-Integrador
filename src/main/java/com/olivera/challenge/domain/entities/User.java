package com.olivera.challenge.domain.entities;

import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.InvalidDataException;
import com.olivera.challenge.domain.exceptions.user.InvalidUserStatusException;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private Long id;
    private String email;
    private String password;
    private UserStatus status;
    private String activationCode;
    private LocalDateTime activationExpiresAt;
    private LocalDateTime createdAt;

    private User(Long id, String password,String email, UserStatus status, String activationCode, LocalDateTime activationExpiresAt, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.activationCode = activationCode;
        this.activationExpiresAt = activationExpiresAt;
        this.createdAt = createdAt;
    }
    //nota:el enunciado no lo aclara, pero el codigo, fecha de cracion y expiración seran generadas por el sistema
    //ya que el usuario al registrarse solo necesita su email y contraseña, establecer mas datos es innecesario
    public static User createUser(String email, String password, LocalDateTime activation, LocalDateTime expires){ //se cambio pq me di cuenta q no se iba a poder testear
      validarDatosNoNulosNiVacios(email, password);
      return new User(null, email, password ,UserStatus.PENDING, UUID.randomUUID().toString(), expires, activation);
    }

    public static void validarDatosNoNulosNiVacios(String email, String password) {
        if(email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty())
            throw new InvalidDataException("Los datos propocionados no son validos");
    }

    public static User reconstructUser(Long id, String password,String email, UserStatus status, String activationCode, LocalDateTime activationExpiresAt, LocalDateTime createdAt) {
        return new User(id, password, email, status, activationCode, activationExpiresAt, createdAt);
    }

    /*
        New users start in PENDING
    Only ACTIVE users may create orders
    status: UserStatus (PENDING, ACTIVE, EXPIRED)
     */

    public boolean isActive(){
        return this.status == UserStatus.ACTIVE;
    }

    public boolean isPending(){
        return this.status == UserStatus.PENDING;
    }

    public boolean isExpired(){
        return this.status==UserStatus.EXPIRED;
    }

    /*
    metodos de cambio
    status: UserStatus (PENDING, ACTIVE, EXPIRED)
     */

    public void toActivate(){
        if(this.status != UserStatus.PENDING){
            throw new InvalidUserStatusException("Only user's with status PENDING can be activated");
        }
        this.status = UserStatus.ACTIVE;
    }
    public void toExpire(){
        this.status = UserStatus.EXPIRED;
    }

    public String getPassword(){
        return password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public LocalDateTime getActivationExpiresAt() {
        return activationExpiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
