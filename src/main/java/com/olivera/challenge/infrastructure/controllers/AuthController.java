package com.olivera.challenge.infrastructure.controllers;

import com.olivera.challenge.application.dto.request.LoginRequest;
import com.olivera.challenge.application.dto.response.LoginResponse;
import com.olivera.challenge.application.port.in.user.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que expone el endpoint POST /api/login.
 *
 * Responsabilidad: recibir el request HTTP, delegar al caso de uso LoginUser
 * y devolver la respuesta correspondiente.
 *
 * Las excepciones (UserNotFoundException → 404, InvalidCredentialsException → 401)
 * son manejadas por GlobalHandlerException.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final LoginUser loginUser;

    public AuthController(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    /**
     * POST /api/login
     * Body: { "email": "...", "password": "..." }
     * Response 200: { "token": "eyJ...", "type": "Bearer" }
     * Response 401: credenciales incorrectas
     * Response 404: email no registrado
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = loginUser.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
