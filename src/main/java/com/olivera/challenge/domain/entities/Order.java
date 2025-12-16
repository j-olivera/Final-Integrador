package com.olivera.challenge.domain.entities;

import com.olivera.challenge.domain.enums.order.OrderStatus;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.domain.exceptions.InvalidDataException;
import com.olivera.challenge.domain.exceptions.order.WrongOrderTransitionException;
import com.olivera.challenge.domain.exceptions.user.InvalidUserStatusException;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
    private Long id;
    private User user;
    private OrderStatus status;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Order(Long id, User user, OrderStatus status, BigDecimal amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Order createOrder(User user, BigDecimal amount, LocalDateTime now){
        dataValidation(user, amount);
        userVerification(user.getStatus());
        return new Order(null, user, OrderStatus.PENDING, amount, now, now);
    }

    public static Order reconstructOrder(Long id, User user, OrderStatus status, BigDecimal amount, LocalDateTime createdAt, LocalDateTime updatedAt){
        return new Order(id, user, status, amount, createdAt, updatedAt);
    }

    private static void dataValidation(User user, BigDecimal amount) {
        if(user == null || amount == null){
            throw new InvalidDataException("Los datos propocionados no son validos");
        }
    }

    private static void userVerification(UserStatus status) {
        if(status != UserStatus.ACTIVE){
            throw new InvalidUserStatusException("User status wanted to be ACTIVE");
        }
    }

    /*
    Valid order transitions:
PENDING -> PROCESSING -> APPROVED
PENDING -> CANCELLED (if UserStatus is EXPIRED)
     */
    public boolean isPending(){
        return status == OrderStatus.PENDING;
    }
    public boolean isProcessing(){
        return status == OrderStatus.PROCESSING;
    }
    public boolean isApproved(){
        return status == OrderStatus.APPROVED;
    }
    //metodos para cambiar estado de order
    public void procces(LocalDateTime now){
        this.status = OrderStatus.PROCESSING;
        this.updatedAt = now; //pq se actualiza
    }
    public void approve(LocalDateTime now){
        if(this.status != OrderStatus.PROCESSING){
            throw new WrongOrderTransitionException("ONLY PROCESSING ORDER CAN APPROVE"); //por seguridad
        }
        this.status = OrderStatus.APPROVED;
        this.updatedAt = now; //pq se actualiza
    }
    public void cancel(LocalDateTime now){
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = now; //pq se actualiza
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
