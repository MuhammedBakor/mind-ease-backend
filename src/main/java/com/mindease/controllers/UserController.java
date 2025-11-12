package com.mindease.controllers;

import com.mindease.DTO.ChangePasswordDTO;
import com.mindease.entities.ReminderEntity;
import com.mindease.entities.UserEntity;
import com.mindease.requests.ReminderRequest;
import com.mindease.services.UserService;
import com.mindease.DTO.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update-profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserDTO userReq) {
        UserDTO updatedProfile = userService.updateProfile(userReq);
        return ResponseEntity
               .ok(updatedProfile);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO passwordReq) {
        userService.changePassword(passwordReq);
        return ResponseEntity
              .noContent()
              .build();
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword() {
        return null;
    }

    @PostMapping("/chat-llm")
    public ResponseEntity<?> chatLlm() {
        return null;
    }

    @PostMapping("/set-reminder")
    public ResponseEntity<?> setReminder(
            @RequestBody ReminderRequest request,
            @AuthenticationPrincipal UserEntity currentUser) {

        ReminderEntity reminder = userService.createReminder(
                request.getNotes(),
                request.getDueDateTime(),
                currentUser
        );

        return ResponseEntity.ok(reminder);
    }

    @GetMapping("/reminders")
    public ResponseEntity<?> getReminders(@AuthenticationPrincipal UserEntity currentUser) {
        List<ReminderEntity> reminders = userService.getReminders(currentUser);
        return ResponseEntity.ok(reminders);
    }

}
