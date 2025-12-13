package com.olivera.challenge.infrastructure.entity;

import com.olivera.challenge.domain.enums.user.UserStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name="User")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String email;
    @Column()
    private String password;
    @Column()
    private UserStatus status;
    @Column()
    private String activationCode;
    @Column()
    private LocalDateTime activationExpiresAt;
    @Column()
    private LocalDateTime createdAt;

    public UserEntity(Long id, String email, String password, UserStatus status, String activationCode, LocalDateTime activationExpiresAt, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.activationCode = activationCode;
        this.activationExpiresAt = activationExpiresAt;
        this.createdAt = createdAt;
    }

    public UserEntity() {

    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public LocalDateTime getActivationExpiresAt() {
        return activationExpiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
