package com.mindease.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.mindease.enums.RoleEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String institutionName;
    private LocalDate registeredDate;
    
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<ReminderEntity> reminderEntity = new ArrayList();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<PasswordResetEntity> passwordResetEntity = new ArrayList();
}
