package com.olivera.challenge.infrastructure.configuration.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuración de Spring Security para la aplicación.
 *
 * Decisiones de diseño:
 * - Usamos JWT stateless → no se crean sesiones HTTP (STATELESS)
 * - Se deshabilita CSRF porque la autenticación es por token, no por cookie de sesión
 * - Los endpoints públicos son: POST /api/login y POST /api/users (registro)
 * - El resto de endpoints requiere un JWT válido en el header Authorization
 * - El PasswordEncoder es BCrypt, tal como indica el SPEC (guideline punto 3)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF: no aplica para APIs REST stateless con JWT
                .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)

            // Configurar las rutas
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos: login y registro de usuarios
                .requestMatchers("/api/login", "/api/users/**").permitAll()
                // Todos los demás endpoints requieren autenticación JWT válida
                .anyRequest().authenticated()
            )

            // Política sin sesión: cada request debe incluir el token JWT
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Agregar nuestro filtro JWT antes del filtro de autenticación estándar de Spring
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * BCryptPasswordEncoder para comparar la password en texto plano del request
     * con el hash almacenado en la base de datos (SPEC guideline punto 3).
     * También es utilizado por RegisterUserImpl para hashear la contraseña al registrar.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
