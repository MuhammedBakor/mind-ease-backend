package com.mindease.DTO;

import com.mindease.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<UserEntity, UserDTO> {

    @Override
    public UserDTO apply(UserEntity userEntity) {
        return new  UserDTO(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getSchoolName(),
                userEntity.getEducationalLevel(),
                userEntity.getGender(),
                userEntity.getDate_of_birth(),
                userEntity.getRole()
        );
    }
}
