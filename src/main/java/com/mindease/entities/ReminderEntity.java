package com.mindease.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
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

    public ReminderEntity(String notes, LocalDateTime setDateTime, LocalDateTime dueDateTime) {
    this.notes = notes;
    this.setDateTime = setDateTime;
    this.dueDateTime = dueDateTime;
   }


}
