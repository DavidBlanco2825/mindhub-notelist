package com.mindhub.todolist.controller;

import com.mindhub.todolist.dto.UserDTO;
import com.mindhub.todolist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "UserController", description = "Operations related to user management")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create User", description = "Creates a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Invalid user data.")))
    })
    public ResponseEntity<UserDTO> createUser(
            @RequestBody
            @Parameter(description = "User data for the new user", required = true) UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User by ID", description = "Returns user information based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User not found.")))
    })
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable("id")
            @Parameter(description = "ID of the user to be retrieved", required = true, example = "1") Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Users", description = "Returns all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User", description = "Updates an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User not found.")))
    })
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("id")
            @Parameter(description = "ID of the user to be updated", required = true, example = "1") Long id,
            @RequestBody
            @Parameter(description = "Updated user data", required = true) UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "Deletes an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted."),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User not found.")))
    })
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id")
            @Parameter(description = "ID of the user to be deleted", required = true, example = "1") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Get User by Username", description = "Returns user information based on the provided username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User not found.")))
    })
    public ResponseEntity<UserDTO> getUserByUsername(
            @PathVariable("username")
            @Parameter(description = "Username of the user to be retrieved", required = true, example = "john_doe") String username) {
        UserDTO userDTO = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/exists/email/{email}")
    @Operation(summary = "Check if Email Exists", description = "Checks if a user with the given email exists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email existence successfully checked.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "boolean", example = "true"))),
    })
    public ResponseEntity<Boolean> checkIfEmailExists(
            @PathVariable("email")
            @Parameter(description = "Email to be checked", required = true, example = "user@example.com") String email) {
        boolean exists = userService.checkIfEmailExists(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @GetMapping("/count/email/{email}")
    @Operation(summary = "Count Users by Email", description = "Counts the number of users with the given email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "integer", example = "1"))),
    })
    public ResponseEntity<Long> countUsersByEmail(
            @PathVariable("email")
            @Parameter(description = "Email for counting users", required = true, example = "user@example.com") String email) {
        long count = userService.countUsersByEmail(email);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
