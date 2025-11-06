package com.mindease.controllers;

import com.mindease.services.UserService;
import com.mindease.DTO.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update-profile")
    public ResponseEntity<Object> updateProfile(RequestBody UserDTO userReq) {
        UserDTO updatedProfile = userService.updateProfile(userReq);
        return ResponseEntity
               .ok(updatedProfile);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword() {
        return null;
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
    public ResponseEntity<?> setReminder() {
        return null;
    }

    @GetMapping("/reminders")
    public ResponseEntity<?> getReminders() {
        return null;
    }

}
