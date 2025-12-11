package com.olivera.challenge.application.dto.response;

import com.olivera.challenge.domain.enums.order.OrderStatus;
import com.olivera.challenge.domain.enums.user.UserStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String email;
    private UserStatus status;
    private LocalDateTime expirationDate;

    public UserResponse(Long id, String email, UserStatus status, LocalDateTime expirationDate) {
        this.id = id;
        this.email = email;
        this.status = status;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
