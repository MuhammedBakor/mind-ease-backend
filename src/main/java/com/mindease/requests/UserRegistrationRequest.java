package com.mindease.requests;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRegistrationRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 100, message = "First name must be between 3 and 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 100, message = "First name must be between 3 and 100 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    private String schoolName;

    private String educationalLevel;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
    private String password;

    private String gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate date_of_birth;

    private String role;

    public UserRegistrationRequest() {}

}
