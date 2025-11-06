package com.mindease.repositories;

import com.mindease.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
