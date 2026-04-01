package com.olivera.challenge.application.dto.response;

// DTO que representa el body de la respuesta exitosa del login
// Contiene el JWT firmado y su tipo (siempre "Bearer")
public class LoginResponse {

    private String token;
    private String type;

    public LoginResponse(String token) {
        this.token = token;
        this.type = "Bearer";  // Tipo fijo según el SPEC
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}
