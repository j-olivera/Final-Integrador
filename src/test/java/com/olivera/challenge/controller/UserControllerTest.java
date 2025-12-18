package com.olivera.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olivera.challenge.application.dto.request.CreateUserRequest;
import com.olivera.challenge.application.dto.response.UserResponse;
import com.olivera.challenge.application.port.in.user.RegisterUser;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.InvalidDataException;
import com.olivera.challenge.domain.exceptions.user.EmailAlreadyExistsException;
import com.olivera.challenge.infrastructure.controllers.user.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockConstructionWithAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockitoBean
    private RegisterUser registerUser;
    @Autowired
    private MockMvc mockMvc; //simula http
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveUserSuccess() throws Exception {
        CreateUserRequest userRequest = new CreateUserRequest("juan@gmail.com","1234561231");
        UserResponse userResponse = new UserResponse(
                1L,
                "juan@gmail.com",
                UserStatus.PENDING,
                LocalDateTime.now().plusDays(7)
                );
        when(registerUser.register(any(CreateUserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())//HTTP 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("juan@gmail.com"))
                .andExpect(jsonPath("$.status").value("PENDING"));

    }
    @Test
    void emailAlreadyExistsValueConflict() throws Exception {
        CreateUserRequest userRequest = new CreateUserRequest("juan@gmail.com","1234561231");

        when(registerUser.register(any(CreateUserRequest.class))).thenThrow(new EmailAlreadyExistsException("Email already exists"));
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isConflict());
    }
    @Test
    void invalidDataValueBadRequest() throws Exception {
        CreateUserRequest userRequest = new CreateUserRequest("juan@gmail.com","1234561231");

        when(registerUser.register(any(CreateUserRequest.class))).thenThrow(new InvalidDataException("The email is required"));
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                        .andExpect(status().isBadRequest());

    }

}
