package com.olivera.challenge.application.dto.request;

import com.olivera.challenge.domain.exceptions.InvalidDataException;

public class CreateUserRequest {
    private String email;
    private String password;
    //demas atributos instanciados por el sistema
    public CreateUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public void validar() {
        if(this.email == null || this.email.trim().isEmpty()){
            throw new InvalidDataException("El email no puede estar vacio");
        }
        if(this.password == null || this.password.trim().isEmpty()){
            throw new InvalidDataException("La contraseña no puede estar vacia");
        }
        if (!this.email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) { //caracteres permiditos de la a-A hasta la z-Z, @ y demas, (.+) significa que debe haber una cadena desp del arroba
            throw new InvalidDataException("Email inválido");
        }
        if (this.password.length() < 6) {
            throw new InvalidDataException("La contraseña debe tener una longitud mayor a 6 caracteres");
        }
    }
}
