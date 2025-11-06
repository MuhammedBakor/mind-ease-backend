package com.mindease.repositories;

import com.mindease.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
