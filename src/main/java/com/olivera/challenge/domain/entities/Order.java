package com.olivera.challenge.domain.entities;

import com.olivera.challenge.domain.enums.order.OrderStatus;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.InvalidDataException;
import com.olivera.challenge.domain.exceptions.user.InvalidUserStatusException;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private User user;
    private OrderStatus status;
    private Double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Order(Long id, User user, OrderStatus status, Double amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order createOrder(User user, Double amount){
        dataValidation(user, amount);
        userVerification(user.getStatus());
        return new Order(null, user, OrderStatus.PENDING, amount, LocalDateTime.now(), null);
    }

    private void dataValidation(User user, Double amount) {
        if(user == null || amount == null ||  amount <= 0){
            throw new InvalidDataException("Los datos propocionados no son validos");
        }
    }

    private void userVerification(UserStatus status) {
        if(status != UserStatus.ACTIVE){
            throw new InvalidUserStatusException("El Usuario debe estar activo");
        }
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
