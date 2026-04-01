package com.olivera.challenge.infrastructure.configuration.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Servicio responsable de generar y validar tokens JWT.
 *
 * - Utiliza JJWT 0.12.x (API fluida)
 * - Firma el token con HS256 usando la clave definida en application.properties
 * - El token tiene una vida útil de 24 horas según el SPEC (punto 4)
 * - Los claims incluyen el id y el email del usuario (SPEC punto 5)
 */
@Component
public class JwtService {

    // La clave secreta se inyecta desde application.properties para no exponerla en el código
    @Value("${jwt.secret}")
    private String secret;

    private static final long EXPIRATION_MS = 1000L * 60 * 60 * 24; // 24 horas en milisegundos

    private SecretKey getSigningKey() {
        // JJWT 0.12 requiere al menos 256 bits para HS256
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un JWT firmado con HS256.
     *
     * @param userId id del usuario
     * @param email  email del usuario
     * @return string del token compacto firmado
     */
    public String generateToken(Long userId, String email) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_MS);

        return Jwts.builder()
                .subject(email)              // sub = email del usuario
                .claim("id", userId)         // claim personalizado: id del usuario (SPEC)
                .claim("email", email)       // claim personalizado: email del usuario (SPEC)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())   // firma con HS256 (JJWT infiere el algoritmo)
                .compact();
    }

    /**
     * Extrae el email (subject) de un token válido.
     *
     * @param token token JWT compacto
     * @return email del usuario
     */
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Valida si el token es correcto (firma y expiración).
     *
     * @param token token JWT compacto
     * @return true si el token es válido
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // Token expirado, firma inválida, malformado, etc.
            return false;
        }
    }
}
