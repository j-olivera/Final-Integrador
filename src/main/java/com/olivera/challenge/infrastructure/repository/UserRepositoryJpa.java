package com.olivera.challenge.infrastructure.repository;

import com.olivera.challenge.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJpa extends JpaRepository<UserEntity,Long> {
}
