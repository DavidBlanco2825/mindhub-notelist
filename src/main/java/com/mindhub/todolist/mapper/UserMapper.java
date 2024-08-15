package com.mindhub.todolist.mapper;

import com.mindhub.todolist.dto.UserDTO;
import com.mindhub.todolist.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return new UserDTO(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail()
        );
    }

    public UserEntity toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());

        return userEntity;
    }
}
