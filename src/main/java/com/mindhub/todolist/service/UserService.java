package com.mindhub.todolist.service;

import com.mindhub.todolist.dto.UserDTO;

import java.util.List;

public interface UserService {

    // CRUD operations
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);

    // Find by specific attributes
    UserDTO getUserByUsername(String username);

    // Utility methods
    boolean checkIfEmailExists(String email);

    long countUsersByEmail(String email);
}
