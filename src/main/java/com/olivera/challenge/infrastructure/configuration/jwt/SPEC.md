# Spec driven development

## 1 - Feature name

jwt-implementation-and-login

## 2 - Description

Implementacion de la autenticación JWT y el caso de uso login para los usuarios,
lo que les permite ver sus ordenes cargardas

## 3 - Endpoint

Method: POST | URL: /api/login

### Request Body
    {
    "email": "[EMAIL_ADDRESS]",
    "password": "[PASSWORD]"
    }

### Expected Responses

- 200 OK: Logeado con exito
    {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer"
    }
- 401 Unauthorizhed: El email exite pero la contraseña no es valida
- 404 Not found: Email no registrado

## 4 - Bussines restrictions (backend)

- Validaciones de campos: El email y el password no pueden ser nulos ni estar vacios en la petición
- La contraseña enviada en texto plano debe coincidir con el hash encriptado almacenado en la base de datos correspondiente al user
- Generacion del token : Si las credenciales son validas, el sistema debe entregar el JWT firmado
- El token JWT generado debe tener un tiempo de vida de 24 horas, pasado ese tiempo debe volver a iniciar sesion

## 5 - Technical Guidelines

- Utiliza la librería JJWT para la creacion y firma del token
- User Spring Security para proteger los endpoints y gestionar el contexto de autenticación
- Utiliza BCryptPasswordEncoder para comparar la contraseña entrante con el hash guardado en al bd
- Los claims del JWT debe incluir la id del usuario y su email

## 6 - Acceptance Criteria

- Escenario 1 - Login exitoso
    - Dado que un usuario intenta iniciar sesion, el email esta registrado y la contraseña enviados a 
     /api/login son correctos, el sistema devuelvo un 200 ok junto con el json que devuelve el JWT token
- Escenario 2 - Credenciales no validas (password incorrecta)
    - Dado que un usario intenta iniciar sesion, el email esta registrado pero la contraseña es incorrecta
    el sistema deniega el acceso, no devuelve el jwt token y devuelve el status 401
- Esceario 3 - Usuario no existente
    - Dado que un usuario intetna iniciar sesion, pero el email no esta registrado, el sistema devuelve un
    status 404 o 401 para mejor seguridad con un mensaje de error adecuado

## 7 - Prompt Utilizado

- Cumple el rol de un Senior Java Developer, estamos construyecdo un backend hosteado en localhost:8080 que se conecta a un front construido con angular
- Implementa lo solicitado en el SPEC.md
- Restricciones: Respeta la arquitectura del sistema (Clean Architecture)
- Entregame los archivos correspondientes y agrega notas para su analisis y comprensión