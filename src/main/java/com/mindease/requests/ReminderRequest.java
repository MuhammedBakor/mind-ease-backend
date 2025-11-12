package com.mindease.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReminderRequest {
    private String notes;
    private LocalDateTime dueDateTime;
}
