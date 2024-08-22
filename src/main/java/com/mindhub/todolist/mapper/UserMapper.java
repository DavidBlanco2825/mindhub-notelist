package com.mindhub.todolist.mapper;

import com.mindhub.todolist.dto.UserRequestDTO;
import com.mindhub.todolist.dto.UserResponseDTO;
import com.mindhub.todolist.entity.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO toResponseDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return new UserResponseDTO(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail()
        );
    }

    public UserEntity toEntity(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequestDTO.getUsername());
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        userEntity.setAuthorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        return userEntity;
    }
}
