package com.mindease.auth;

import com.mindease.DTO.UserDTO;
import com.mindease.DTO.UserDTOMapper;
import com.mindease.entities.UserEntity;
import com.mindease.exceptions.DuplicateResourceException;
import com.mindease.auth.jwt.JWTUtil;
import com.mindease.repositories.UserRepository;
import com.mindease.requests.LoginRequest;
import com.mindease.requests.UserRegistrationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDTOMapper userDTOMapper, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDTOMapper = userDTOMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public void registerUser(UserRegistrationRequest userToRegister) {

        String email = userToRegister.getEmail();

        if (userRepository.existsByEmail(email)){
            throw new DuplicateResourceException("Email already exists");
        }

        UserEntity newUser = new UserEntity(
                userToRegister.getFirstName(),
                userToRegister.getLastName(),
                userToRegister.getEmail(),
                userToRegister.getSchoolName(),
                userToRegister.getEducationalLevel(),
                passwordEncoder.encode(userToRegister.getPassword()),
                userToRegister.getGender(),
                userToRegister.getDate_of_birth(),
                userToRegister.getRole()
        );

        userRepository.save(newUser);

    }

    public AuthResponse login(LoginRequest request){

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            UserEntity principal = (UserEntity) authentication.getPrincipal();
            UserDTO userDTO = userDTOMapper.apply(principal);
            String token = jwtUtil.issueToken(userDTO.email(), userDTO.role());

            return new AuthResponse(token, userDTO);

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid email or password");
        } catch (Exception e) {
            throw new RuntimeException("Error occurs  while log in:  " + e.getMessage());
        }

    }
}
