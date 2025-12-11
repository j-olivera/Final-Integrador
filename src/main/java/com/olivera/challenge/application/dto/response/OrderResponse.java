package com.olivera.challenge.application.dto.response;

import com.olivera.challenge.domain.enums.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {
    private Long id;
    private String userName;
    private BigDecimal amount;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;

    public OrderResponse(Long id, String userName, BigDecimal amount, OrderStatus orderStatus, LocalDateTime createdAt) {
        this.id = id;
        this.userName = userName;
        this.amount = amount;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
