package com.olivera.challenge.application.port.in.user;

import com.olivera.challenge.application.dto.request.LoginRequest;
import com.olivera.challenge.application.dto.response.LoginResponse;

// Puerto de entrada (driving port) para el caso de uso de login
// Definido en la capa de aplicación, implementado por LoginUserImpl
public interface LoginUser {
    LoginResponse login(LoginRequest request);
}
