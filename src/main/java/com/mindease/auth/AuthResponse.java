package com.mindease.auth;


import com.mindease.DTO.UserDTO;

public record AuthResponse(
        String token, UserDTO userDTO
) {
}
