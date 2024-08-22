package com.mindhub.todolist.service;

import com.mindhub.todolist.dto.UserRequestDTO;
import com.mindhub.todolist.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    // CRUD operations
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);

    UserResponseDTO updateUser(String username, UserRequestDTO userRequestDTO);

    void deleteUser(Long id);

    // Find by specific attributes
    UserResponseDTO getUserByUsername(String username);

    // Utility methods
    boolean checkIfEmailExists(String email);

    long countUsersByEmail(String email);
}
