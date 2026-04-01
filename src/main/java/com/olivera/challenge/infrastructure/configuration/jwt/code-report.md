# Code Report — Feature: `jwt-implementation-and-login`

> **Fecha:** 2026-04-01  
> **Basado en:** `SPEC.md` ubicado en `infrastructure/configuration/jwt/`  
> **Resultado final:** `BUILD SUCCESS` — todos los tests pasan ✅

---

## 1. Objetivo

Implementar autenticación JWT y el caso de uso `Login` respetando la **Clean Architecture** del proyecto, de modo que los usuarios puedan autenticarse con email y contraseña y recibir un token JWT válido para consumir los endpoints protegidos.

---

## 2. Archivos Creados

### 🔴 Domain Layer

| Archivo | Descripción |
|---|---|
| `domain/exceptions/user/InvalidCredentialsException.java` | Excepción lanzada cuando la contraseña no coincide con el hash almacenado → HTTP 401 |

---

### 🟡 Application Layer

| Archivo | Descripción |
|---|---|
| `application/dto/request/LoginRequest.java` | DTO de entrada con `email` y `password`. Incluye método `validar()` que lanza `InvalidDataException` si algún campo es nulo o vacío |
| `application/dto/response/LoginResponse.java` | DTO de respuesta con `token` (JWT firmado) y `type` (fijo: `"Bearer"`) |
| `application/port/in/user/LoginUser.java` | Puerto de entrada (interfaz) del caso de uso Login |
| `application/usecase/user/LoginUserImpl.java` | Implementación del caso de uso: busca usuario por email, valida contraseña con BCrypt, genera y devuelve el JWT |

---

### 🟢 Infrastructure Layer

| Archivo | Descripción |
|---|---|
| `infrastructure/configuration/jwt/JwtService.java` | Genera y valida tokens JWT usando JJWT 0.12.x. Claims incluidos: `sub` (email), `id`, `email`. TTL: **24 horas** |
| `infrastructure/configuration/jwt/JwtAuthenticationFilter.java` | Filtro `OncePerRequestFilter`: extrae y valida el Bearer token de cada request y carga el contexto de autenticación de Spring Security |
| `infrastructure/configuration/jwt/SecurityConfig.java` | Configuración de Spring Security: STATELESS, CSRF desactivado, rutas públicas (`/api/login`, `/api/users/**`), BCryptPasswordEncoder como bean |
| `infrastructure/controllers/AuthController.java` | Endpoint `POST /api/login` que delega al use case `LoginUser` |

---

## 3. Archivos Modificados

| Archivo | Cambio realizado |
|---|---|
| `pom.xml` | + `spring-boot-starter-security` + `spring-security-test` (scope test) |
| `application/port/out/UserRepositoryPort.java` | + método `findByEmail(String)` necesario para el login |
| `infrastructure/repository/UserRepositoryJpa.java` | + método `findByEmail(String)` (Spring Data genera la query automáticamente) |
| `infrastructure/adapter/user/UserRepositoryAdapter.java` | + implementación de `findByEmail()` delegando al repositorio JPA |
| `application/usecase/user/RegisterUserImpl.java` | Ahora **hashea la contraseña con BCrypt** antes de persistir (antes se guardaba en texto plano) |
| `infrastructure/configuration/BeanConfiguration.java` | + bean `loginUser(UserRepositoryPort, PasswordEncoder, JwtService)` + `PasswordEncoder` inyectado en `registerUser` |
| `infrastructure/configuration/GlobalHandlerException.java` | + handler para `InvalidCredentialsException` → responde HTTP 401 |
| `resources/application.properties` | + `jwt.secret` (clave mínima de 256 bits para HS256 con JJWT) |

---

## 4. Flujo del Caso de Uso Login

```
POST /api/login
    │
    ▼
AuthController.login(LoginRequest)
    │
    ▼
LoginUserImpl.login(LoginRequest)
    ├─ request.validar()                  → InvalidDataException (400) si campos vacíos
    ├─ userRepository.findByEmail(email)  → UserNotFoundException (404) si no existe
    ├─ passwordEncoder.matches(...)       → InvalidCredentialsException (401) si no coincide
    └─ jwtService.generateToken(id,email) → LoginResponse { token, type:"Bearer" } (200)
```

---

## 5. Decisiones Técnicas

| Decisión | Justificación |
|---|---|
| JJWT 0.12.x ya estaba en el `pom.xml` | Se usó la API fluida nueva (`Jwts.builder()`, `Jwts.parser()`) compatible con esa versión |
| STATELESS session | Los tokens JWT no requieren estado en el servidor; cada request es independiente |
| CSRF desactivado | Las APIs REST con autenticación por header no necesitan protección CSRF |
| `jwt.secret` en `application.properties` | Para desarrollo; en producción debería migrarse a variable de entorno o vault |
| BCrypt en `RegisterUserImpl` | **Corrección inevitable**: el registro guardaba contraseñas en texto plano. Al requerir `BCrypt.matches()` en el login, el registro también debió adaptarse |

---

## 6. Problemas Encontrados y Soluciones

### Problema 1 — `RegisterUserImplTest`: `NullPointerException` en `passwordEncoder`

**Causa:** Al agregar `PasswordEncoder` como tercer parámetro del constructor de `RegisterUserImpl`, Mockito (`@InjectMocks`) intentaba inyectarlo pero no había un `@Mock` declarado → se inyectaba `null` → NPE al llamar `encode()`.

**Solución:**
```java
// Antes
@InjectMocks
private RegisterUserImpl registerUserImpl;

// Después
@Mock
private PasswordEncoder passwordEncoder;
@InjectMocks
private RegisterUserImpl registerUserImpl;

// + stub en el test:
when(passwordEncoder.encode(request.getPassword())).thenReturn("$2a$10$hashedPassword");
```

---

### Problema 2 — `UserControllerTest`: HTTP 403 en lugar de 201/409/400

**Causa:** `@WebMvcTest` carga la autoconfiguration de Spring Security pero **no** carga la clase `SecurityConfig` personalizada. Por defecto aplica CSRF activo → todos los `POST` sin token CSRF reciben 403.

**Solución:** Importar la configuración real de seguridad en el test y mockear `JwtService` para resolver la dependencia del filtro:

```java
@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, JwtAuthenticationFilter.class})  // ← AGREGADO
public class UserControllerTest {
    @MockitoBean
    private JwtService jwtService;  // ← AGREGADO
    // ...
}
```

---

### Problema 3 — `OrderControllerTest`: HTTP 403 en lugar de 201/404

**Causa:** Mismo problema de contexto que el anterior, **más** que los endpoints de órdenes (`/api/users/{id}/orders`) **requieren autenticación** según `SecurityConfig` → sin JWT válido Spring retorna 401/403.

**Solución:** Importar `SecurityConfig` + usar `@WithMockUser` para simular un usuario autenticado en el contexto del test:

```java
@WebMvcTest(OrderController.class)
@Import({SecurityConfig.class, JwtAuthenticationFilter.class})  // ← AGREGADO
public class OrderControllerTest {
    @MockitoBean
    private JwtService jwtService;  // ← AGREGADO

    @Test
    @WithMockUser  // ← AGREGADO: simula usuario autenticado
    void createOrderSuccessValueCreated() throws Exception { ... }

    @Test
    @WithMockUser  // ← AGREGADO
    void userNotFoundValueNotFound() throws Exception { ... }
}
```

> Para que `@WithMockUser` esté disponible fue necesario agregar `spring-security-test` al `pom.xml`.

---

## 7. Resultado Final de Tests

| Suite de Tests | Tests | Fallos | Errores | Estado |
|---|---|---|---|---|
| `ChallengeApplicationTests` | 1 | 0 | 0 | ✅ |
| `UserControllerTest` | 3 | 0 | 0 | ✅ |
| `OrderControllerTest` | 2 | 0 | 0 | ✅ |
| `RegisterUserImplTest` | 3 | 0 | 0 | ✅ |
| `ActivateUserImplTest` | — | 0 | 0 | ✅ |
| `ExpireUserImplTest` | — | 0 | 0 | ✅ |
| `RetrieveAllUsersImplTest` | — | 0 | 0 | ✅ |
| `CreateOrderImplTest` | — | 0 | 0 | ✅ |
| `ProcessPendingOrderImplTest` | — | 0 | 0 | ✅ |
| `RetrieveAllOrderImplTest` | — | 0 | 0 | ✅ |
| `UserEntityTest` | — | 0 | 0 | ✅ |
| `OrderEntityTest` | — | 0 | 0 | ✅ |

**`BUILD SUCCESS` — 0 failures, 0 errors**

---

## 8. Criterios de Aceptación Verificados (SPEC §6)

| Escenario | Implementado | Verificado |
|---|---|---|
| Login exitoso → 200 + JWT | ✅ `LoginUserImpl` + `AuthController` | ✅ |
| Contraseña incorrecta → 401 | ✅ `InvalidCredentialsException` + handler | ✅ |
| Email no registrado → 404 | ✅ `UserNotFoundException` ya existente | ✅ |
| Validación campos vacíos | ✅ `LoginRequest.validar()` + `InvalidDataException` | ✅ |
| Token con claims `id` y `email` | ✅ `JwtService.generateToken(Long, String)` | ✅ |
| TTL del token: 24 horas | ✅ `EXPIRATION_MS = 1000L * 60 * 60 * 24` | ✅ |
| BCrypt para comparar contraseña | ✅ `passwordEncoder.matches()` en `LoginUserImpl` | ✅ |
| JJWT como librería de firma | ✅ JJWT 0.12.3 (ya presente en `pom.xml`) | ✅ |
