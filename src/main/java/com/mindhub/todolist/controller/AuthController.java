package com.mindhub.todolist.controller;

import com.mindhub.todolist.dto.JwtAuthenticationResponse;
import com.mindhub.todolist.dto.LoginRequest;
import com.mindhub.todolist.dto.UserRequestDTO;
import com.mindhub.todolist.dto.UserResponseDTO;
import com.mindhub.todolist.exception.UserEntityValidator;
import com.mindhub.todolist.service.AuthService;
import com.mindhub.todolist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "Operations related to user authentication and registration")
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
    @Operation(summary = "Register User", description = "Registers a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User registered successfully"))),
            @ApiResponse(responseCode = "400", description = "Invalid input. Validation errors or missing fields.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Invalid user data."))),
            @ApiResponse(responseCode = "409", description = "Conflict. Username or email already exists.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Username or email already in use."))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "An error occurred while processing your request.")))
    })
    public ResponseEntity<String> registerUser(
            @RequestBody
            @Parameter(description = "User data for registration", required = true,
                       content = @Content(
                            schema = @Schema(implementation = UserRequestDTO.class),
                            examples = @ExampleObject(value = "{\"username\":\"yourUsername\",\"email\":\"yourEmail@email.com\",\"password\":\"your#Passw0rd\"}")
                       )) UserRequestDTO userRequestDTO) {

        userEntityValidator.validate(userRequestDTO);
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate User", description = "Authenticates a user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Invalid username or password."))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "An error occurred while processing your request.")))
    })
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(
            @RequestBody
            @Parameter(description = "Login request with username and password", required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginRequest.class),
                            examples = @ExampleObject(value = "{\"username\":\"yourUsername\",\"password\":\"your#Passw0rd\"}")
                    )) LoginRequest loginRequest) {

        JwtAuthenticationResponse authResponse = authService.authenticateUser(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
