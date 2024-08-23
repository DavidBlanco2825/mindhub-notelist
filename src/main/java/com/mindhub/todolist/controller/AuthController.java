package com.mindhub.todolist.controller;

import com.mindhub.todolist.dto.JwtAuthenticationResponse;
import com.mindhub.todolist.dto.LoginRequest;
import com.mindhub.todolist.dto.UserRequestDTO;
import com.mindhub.todolist.dto.UserResponseDTO;
import com.mindhub.todolist.exception.UserEntityValidator;
import com.mindhub.todolist.service.AuthService;
import com.mindhub.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserEntityValidator userEntityValidator;

    @Autowired
    public AuthController(AuthService authService, UserService userService, UserEntityValidator userEntityValidator) {
        this.authService = authService;
        this.userService = userService;
        this.userEntityValidator = userEntityValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDTO userRequestDTO) {

        userEntityValidator.validate(userRequestDTO);

        UserResponseDTO createdUser = userService.createUser(userRequestDTO);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

        JwtAuthenticationResponse authResponse = authService.authenticateUser(loginRequest);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
