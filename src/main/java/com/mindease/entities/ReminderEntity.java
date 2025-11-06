package com.mindease.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "reminders")
public class ReminderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String notes;

    private LocalDateTime setDateTime;   // when the reminder was created
    private LocalDateTime dueDateTime;   // when it should trigger


}