package com.mindease.services;

import com.mindease.DTO.ChangePasswordDTO;
import com.mindease.DTO.UserDTO;
import com.mindease.entities.ReminderEntity;
import com.mindease.entities.UserEntity;
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


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ReminderRepository reminderRepository;
    private final EmailService emailService;
    private final ReminderSchedulerService reminderSchedulerService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ReminderRepository reminderRepository, EmailService emailService, ReminderSchedulerService reminderSchedulerService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.reminderRepository = reminderRepository;
        this.emailService = emailService;
        this.reminderSchedulerService = reminderSchedulerService;
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
    public void resetPassword(PasswordResetDTO passwordResetDTO){
         // validate for empty email
        if (passwordResetDTO == null){
          throw new IllegalArgumentException("email cannot be empty");
       }
        
        // Get current user details
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found in DB"));
      
        // compare reset email sent by user to what is in the db
        if (passwordResetDTO.email != user.getEmail()){
           throw new Exception("unauthorized reset email provided");
        }

        // generate reser token,save in db and send reset link to user’s email addr
        String resetToken = String.valueOf(LocalDateTime.now()) + UUID.getRandomUUID();
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

