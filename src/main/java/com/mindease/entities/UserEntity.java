package com.mindease.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String schoolName;

    private String educationalLevel;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String gender;

    private LocalDate date_of_birth;

    @Column(nullable = false)
    private String role;

    public UserEntity(
            String firstName, String lastName,
            String email, String schoolName,
            String educationalLevel, String password,
            String gender, LocalDate date_of_birth,
            String role) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.schoolName = schoolName;
        this.educationalLevel = educationalLevel;
        this.password = password;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return  email;
    }
}
