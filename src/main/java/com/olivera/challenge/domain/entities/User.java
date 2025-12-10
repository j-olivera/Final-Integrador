package com.olivera.challenge.domain.entities;

import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.InvalidDataException;
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
    private User createUser(String email, String password){
      validarDatosNoNulosNiVacios(email, password);
      LocalDateTime now = LocalDateTime.now();
      return new User(null, email, password ,UserStatus.PENDING, UUID.randomUUID().toString(), now.plusDays(7), now);
    }

    private void validarDatosNoNulosNiVacios(String email, String password) {
        if(email == null || email.isEmpty() || password == null || password.isEmpty())
            throw new InvalidDataException("Los datos propocionados no son validos");
    }

    //falta de metodo reconstruir desde bd

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
