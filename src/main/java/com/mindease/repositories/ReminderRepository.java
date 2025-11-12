package com.mindease.repositories;

import com.mindease.entities.ReminderEntity;
import com.mindease.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReminderRepository extends JpaRepository <ReminderEntity, UUID>{
    List<ReminderEntity> findByUser(UserEntity user);
}
