package com.mindease.auth;

import com.mindease.auth.jwt.JWTUtil;
import com.mindease.requests.LoginRequest;
import com.mindease.requests.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;

    public AuthController(AuthService authService, JWTUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest userToRegister) {

        authService.registerUser(userToRegister);
        String jwtToken = jwtUtil.issueToken(userToRegister.getEmail(), userToRegister.getRole());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        AuthResponse response = authService.login(loginRequest);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, response.token())
                .body(response);
    }

}
