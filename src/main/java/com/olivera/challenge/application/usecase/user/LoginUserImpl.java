package com.olivera.challenge.application.usecase.user;

import com.olivera.challenge.application.dto.request.LoginRequest;
import com.olivera.challenge.application.dto.response.LoginResponse;
import com.olivera.challenge.application.port.in.user.LoginUser;
import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.exceptions.user.InvalidCredentialsException;
import com.olivera.challenge.domain.exceptions.user.UserNotFoundException;
import com.olivera.challenge.infrastructure.configuration.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Caso de uso: Login de usuario.
 *
 * Flujo:
 * 1. Valida que email y password no estén vacíos (SPEC punto 4)
 * 2. Busca el usuario por email → 404 si no existe (SPEC escenario 3)
 * 3. Compara la password en texto plano con el hash de la BD usando BCrypt (SPEC punto 4 y guideline)
 * 4. Si las credenciales son válidas genera y devuelve el JWT (SPEC escenario 1)
 * 5. Si la password es incorrecta → 401 (SPEC escenario 2)
 */
public class LoginUserImpl implements LoginUser {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;   // BCryptPasswordEncoder inyectado via BeanConfiguration
    private final JwtService jwtService;

    public LoginUserImpl(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // Paso 1: validar que los campos no estén vacíos
        request.validar();

        // Paso 2: buscar el usuario por email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(
                        "No se encontró un usuario con el email: " + request.getEmail()
                )); // → 404

        // Paso 3: comparar la contraseña en texto plano con el hash almacenado
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordMatches) {
            throw new InvalidCredentialsException("Las credenciales proporcionadas no son válidas"); // → 401
        }

        // Paso 4: generar JWT con id y email en los claims (SPEC guideline punto 4)
        String token = jwtService.generateToken(user.getId(), user.getEmail());
        return new LoginResponse(token);
    }
}
