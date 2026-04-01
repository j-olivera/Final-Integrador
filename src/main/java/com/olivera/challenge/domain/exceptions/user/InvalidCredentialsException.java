package com.olivera.challenge.domain.exceptions.user;

// Excepción lanzada cuando el email existe pero la contraseña no coincide (401)
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
