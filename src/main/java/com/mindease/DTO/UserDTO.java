package com.mindease.DTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String schoolName,
        String educationalLevel,
        String gender,
        LocalDate date_of_birth,
        String role

) {
}
