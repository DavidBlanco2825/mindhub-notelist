package com.mindhub.todolist.service;

import com.mindhub.todolist.dto.JwtAuthenticationResponse;
import com.mindhub.todolist.dto.LoginRequest;

public interface AuthService {

    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);
}
