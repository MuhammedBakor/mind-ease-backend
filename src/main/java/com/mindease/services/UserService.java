package com.mindease.services;

import com.mindease.DTO.ChangePasswordDTO;
import com.mindease.DTO.NewPasswordDTO;
import com.mindease.DTO.UserDTO;
import com.mindease.DTO.ResetPasswordDTO;

import com.mindease.entities.PasswordResetEntity;
import com.mindease.entities.ReminderEntity;
import com.mindease.entities.UserEntity;

import com.mindease.services.EmailService;

import com.mindease.repositories.PasswordResetRepository;
import com.mindease.repositories.ReminderRepository;
import com.mindease.repositories.UserRepository;

import jakarta.transaction.Transactional;
import org.quartz.SchedulerException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetRepository passwordResetRepository;
    private final ReminderRepository reminderRepository;
    private final EmailService emailService;
    private final ReminderSchedulerService reminderSchedulerService;

    public UserService(
            UserRepository userRepository, PasswordEncoder passwordEncoder,
            ReminderRepository reminderRepository, EmailService emailService,
            ReminderSchedulerService reminderSchedulerService, PasswordResetRepository passwordResetRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.reminderRepository = reminderRepository;
        this.emailService = emailService;
        this.reminderSchedulerService = reminderSchedulerService;
        this.passwordResetRepository = passwordResetRepository;
    }

/*
* Service for handling user's profilei info update
* for and by authenticated users
* author: Augustine Alul 
    |  |
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
        if (userReq.email() != null) user.setEmail(userReq.email());
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
*  * author: Augustine Alul 
    |  |
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
    

    /*
    * Service method that enables password reset
    * for authenticated users, by verifying their
    * emails and sending them a reset link
    * author: Augustine Alul
    *      |  |
    *     \    /
    *      \  /
    *       \/ 
    */

    @Transactional
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {

        if (resetPasswordDTO == null || resetPasswordDTO.email() == null){
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // Get user from DB (reset requests can be done even if user is not logged in)
        UserEntity user = userRepository
                .findByEmail(resetPasswordDTO.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate random token
        String resetToken = UUID.randomUUID().toString();

        // Save reset request
        PasswordResetEntity resetObject = new PasswordResetEntity();
        resetObject.setUser(user);
        resetObject.setResetToken(resetToken);
        resetObject.setCreatedAt(LocalDateTime.now());
        resetObject.setUsed(false);

        passwordResetRepository.save(resetObject);

        // Build reset link
        String link = resetPasswordDTO.redirectURL()
                + "?token=" + resetToken;

        // Send Email
        emailService.sendEmail(
                user.getEmail(),
                "🔐 Reset Your Password",
                "Use the link below to reset your password:\n" + link
        );

        return "Reset email sent successfully!";
    }

    @Transactional
    public String confirmPasswordReset(NewPasswordDTO dto) {

        PasswordResetEntity resetEntry = passwordResetRepository
                .findByResetToken(dto.token())
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        if (resetEntry.isUsed()) {
            throw new RuntimeException("This reset link has already been used");
        }


        if (resetEntry.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(15))) {
            throw new RuntimeException("Reset token expired");
        }

        UserEntity user = resetEntry.getUser();
        user.setPassword(passwordEncoder.encode(dto.newPassword()));

        userRepository.save(user);

        resetEntry.setUsed(true);
        passwordResetRepository.save(resetEntry);

        return "Password updated successfully!";
    }





    // Create a reminder for a user
    public ReminderEntity createReminder(String notes, LocalDateTime dueDateTime, UserEntity user) {
        ReminderEntity reminder = new ReminderEntity();
        reminder.setNotes(notes);
        reminder.setSetDateTime(LocalDateTime.now());
        reminder.setDueDateTime(dueDateTime);
        reminder.setUser(user);
        reminder.setSent(false);

        ReminderEntity savedReminder = reminderRepository.save(reminder);

        try {
            reminderSchedulerService.scheduleReminder(savedReminder);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return savedReminder;
    }

    // To get all reminders for a user
    public List<ReminderEntity> getReminders(UserEntity user) {
        return reminderRepository.findByUser(user);
    }

}




