package com.olivera.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olivera.challenge.application.dto.request.CreateOrderRequest;
import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.dto.response.UserResponse;
import com.olivera.challenge.application.port.in.order.CreateOrder;
import com.olivera.challenge.domain.enums.order.OrderStatus;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.user.UserNotFoundException;
import com.olivera.challenge.infrastructure.controllers.order.OrderController;
import com.olivera.challenge.infrastructure.configuration.jwt.JwtAuthenticationFilter;
import com.olivera.challenge.infrastructure.configuration.jwt.JwtService;
import com.olivera.challenge.infrastructure.configuration.jwt.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mockConstructionWithAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@WebMvcTest(OrderController.class)
@Import({SecurityConfig.class, JwtAuthenticationFilter.class})
public class OrderControllerTest {
    @MockitoBean
    private CreateOrder createOrder;
    @MockitoBean
    private JwtService jwtService; // necesario para que Spring Security pueda crear JwtAuthenticationFilter en tests WebMvcTest
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser // simula usuario autenticado; el endpoint requiere autenticacion segun SecurityConfig
    void createOrderSuccessValueCreated() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest(new BigDecimal(123));
        UserResponse userResponse = new UserResponse(
                1L,
                "juan@gmail.com",
                UserStatus.ACTIVE,
                LocalDateTime.now().minusDays(3)
        );

        OrderResponse response = new OrderResponse(
                1L,
                userResponse.getEmail(),
                request.getAmount(),
                OrderStatus.PENDING,
                LocalDateTime.now()
        );
        when(createOrder.createOrder(any(CreateOrderRequest.class),anyLong())).thenReturn(response);

        mockMvc.perform(post("/api/users/{userId}/orders", userResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
    @Test
    @WithMockUser // simula usuario autenticado
    void userNotFoundValueNotFound() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest(new BigDecimal(123));
        when(createOrder.createOrder(any(CreateOrderRequest.class),anyLong())).thenThrow(new UserNotFoundException("User not found"));
        mockMvc.perform(post("/api/users/{userId}/orders",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
