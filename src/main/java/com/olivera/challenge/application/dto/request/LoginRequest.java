package com.olivera.challenge.application.dto.request;

import com.olivera.challenge.domain.exceptions.InvalidDataException;

// DTO que representa el body del request POST /api/login
public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Validacion: el email y la password no pueden ser nulos ni estar vacios (SPEC punto 4)
    public void validar() {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException("El email no puede ser nulo ni estar vacío");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidDataException("La contraseña no puede ser nula ni estar vacía");
        }
    }
}
