package com.olivera.challenge.infrastructure.configuration.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro que intercepta cada request y valida el token JWT del header Authorization.
 *
 * - Se ejecuta una sola vez por request (OncePerRequestFilter)
 * - Si el header es "Authorization: Bearer <token>" y el token es válido,
 *   establece la autenticación en el SecurityContext de Spring Security
 * - Los endpoints públicos (como /api/login y /api/users) son configurados
 *   en SecurityConfig para pasar sin autenticación
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Si no viene el header o no tiene el prefijo "Bearer ", pasa al siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // quitar "Bearer "

        // Valida el token; si es inválido o expirado, no establece autenticación
        if (jwtService.isTokenValid(token)) {
            String email = jwtService.extractEmail(token);

            // Crea el objeto de autenticación con el email como principal (sin roles por ahora)
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, List.of());

            // Registra la autenticación en el contexto de seguridad de la sesión actual
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
