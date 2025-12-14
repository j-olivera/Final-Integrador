package com.olivera.challenge.infrastructure.repository;

import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.user.UserStatus;
import com.olivera.challenge.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepositoryJpa extends JpaRepository<UserEntity,Long> {
    boolean existsByEmail(String email);
    List<UserEntity> findByStatus(UserStatus status);
}
