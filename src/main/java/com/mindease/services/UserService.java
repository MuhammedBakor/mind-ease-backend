package com.mindease.services;

import jakarta.transaction.Transactional;
import com.mindease.entities.UserEntity;
import com.mindease.repositories.UserRepository;
import com.mindease.DTO.UserDTO;
import com.mindease.DTO.ChangePasswordDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

/*
* Service for handling user's profilei info update
* for and by authenticated users
*   |  |
*  \    /
*   \  /
*    \/
*/
    public UserDTO updateProfile(UserDTO userReq) {
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

       return new UserDTO(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getSchoolName(),
        user.getEducationalLevel(),
        user.getGender(),
        user.getDate_of_birth(),
        user.getRole()
        );
    }

/*
* Service for handling change of passwords
* for authenticated users
*   |  |
*  \    /
*   \  /
*    \/
*/
    @Transactional
public void changePassword(ChangePasswordDTO passwordReq) {
    // Validate for empty request
    if (passwordReq == null) {
        throw new IllegalArgumentException("Reset password information cannot be empty");
    }
   
    // Get current user details
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = userRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found in DB"));

    // Compare old password before changing
    if (passwordEncoder.matches(passwordReq.oldPassword(), user.getPassword())) {
        user.setPassword(passwordEncoder.encode(passwordReq.newPassword()));
        userRepository.save(user);
    } else {
        throw new IllegalArgumentException("Enter correct old password");
    }
}

}
