package com.mindease.services;

import com.mindease.repositories.UserRepository;
import com.mindease.DTO.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
   public Void updateProfile(UserDTO userReq){
    if (userReq == null){
        throw new IllegalArgunentException("no update information provided");
    }
       
    // get email identity from security context
    String email = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getName();

    // confirm if user exists in DB
    UserEntity user = userRepository
                     .findByEmail(email)
                     .orElseThrow(() -> new Exception("user not found in"));

    if (userReq.getFirstName != null){
        userRepository.save(user.setFirstName(userReq.getFirstName());    
   }

    
}
