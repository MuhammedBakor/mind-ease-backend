package com.mindease.repositories;

import com.mindease.entities.PasswordResetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetEntity, UUID> {

    Optional<PasswordResetEntity> findByResetToken(String resetToken);

    Optional<PasswordResetEntity> findByUserId(UUID userId);

    void deleteByUserId(UUID userId);
}
