package com.mindease.services;

import com.mindease.entities.UserEntity;
import com.mindease.repositories.UserRepository;
import com.mindease.DTO.UserDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateProfile(UserDTO userReq) {
        if (userReq == null) {
            throw new IllegalArgumentException("No update information provided");
        }

        // Get current user's email from security context
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch the user from DB
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // updating non-null fields
        if (userReq.firstName() != null) user.setFirstName(userReq.firstName());
        if (userReq.lastName() != null) user.setLastName(userReq.lastName());
        if (userReq.schoolName() != null) user.setSchoolName(userReq.schoolName());
        if (userReq.educationalLevel() != null) user.setEducationalLevel(userReq.educationalLevel());
        if (userReq.gender() != null) user.setGender(userReq.gender());
        if (userReq.date_of_birth() != null) user.setDate_of_birth(userReq.date_of_birth());
       
        userRepository.save(user);
    }
}
