package com.mindhub.todolist.service.impl;

import com.mindhub.todolist.dto.UserRequestDTO;
import com.mindhub.todolist.dto.UserResponseDTO;
import com.mindhub.todolist.entity.UserEntity;
import com.mindhub.todolist.exception.ResourceNotFoundException;
import com.mindhub.todolist.mapper.UserMapper;
import com.mindhub.todolist.repository.UserRepository;
import com.mindhub.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mindhub.todolist.config.Constans.USER_NOT_FOUND_ID;
import static com.mindhub.todolist.config.Constans.USER_NOT_FOUND_USERNAME;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = userMapper.toEntity(userRequestDTO);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.toResponseDto(savedUserEntity);
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_ID + userId));
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @Override
    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_ID + userId));

        existingUser.setUsername(userRequestDTO.getUsername());
        existingUser.setEmail(userRequestDTO.getEmail());

        UserEntity updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(USER_NOT_FOUND_ID + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_USERNAME + username));
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public long countUsersByEmail(String email) {
        return userRepository.countByEmail(email);
    }
}
