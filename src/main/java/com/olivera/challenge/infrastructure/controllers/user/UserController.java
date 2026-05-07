package com.olivera.challenge.infrastructure.controllers.user;

import com.olivera.challenge.application.dto.request.CreateUserRequest;
import com.olivera.challenge.application.dto.response.UserResponse;
import com.olivera.challenge.application.port.in.user.RegisterUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
@CrossOrigin("http://localhost:4200")
public class UserController {
    private final RegisterUser registerUser;

    public UserController(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody CreateUserRequest createUserRequest) {
        UserResponse response = registerUser.register(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
