package com.olivera.challenge.infrastructure.entity;

import com.olivera.challenge.domain.enums.order.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name="orders") //order es palabra reservada de sql eje
@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    @JoinColumn()
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;
    @Column(nullable=false)
    private OrderStatus status;
    @Column(nullable=false)
    private BigDecimal amount;
    @Column(nullable=false)
    private LocalDateTime createdAt;
    @Column(nullable=false)
    private LocalDateTime updatedAt;

    public OrderEntity(Long id, UserEntity user, OrderStatus status, BigDecimal amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public OrderEntity() {

    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
