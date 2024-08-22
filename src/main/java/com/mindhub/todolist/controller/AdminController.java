package com.mindhub.todolist.controller;

import com.mindhub.todolist.dto.TaskRequestDTO;
import com.mindhub.todolist.dto.TaskResponseDTO;
import com.mindhub.todolist.dto.UserRequestDTO;
import com.mindhub.todolist.dto.UserResponseDTO;
import com.mindhub.todolist.service.TaskService;
import com.mindhub.todolist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/admin")
public class AdminController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public AdminController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    // User management
    @PostMapping("/users")
    @Operation(summary = "Create User", description = "Creates a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Invalid user data.")))
    })
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody
            @Parameter(description = "User data for the new user", required = true) UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get User by ID", description = "Returns user information based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User not found.")))
    })
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable("id")
            @Parameter(description = "ID of the user to be retrieved", required = true, example = "1") Long id) {
        UserResponseDTO userRequestDTO = userService.getUserById(id);
        return new ResponseEntity<>(userRequestDTO, HttpStatus.OK);
    }

    @GetMapping("/users")
    @Operation(summary = "Get All Users", description = "Returns all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> userRequestDTOS = userService.getAllUsers();
        return new ResponseEntity<>(userRequestDTOS, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Update User", description = "Updates an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User not found.")))
    })
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable("id")
            @Parameter(description = "ID of the user to be updated", required = true, example = "1") Long id,
            @RequestBody
            @Parameter(description = "Updated user data", required = true) UserRequestDTO userRequestDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
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

    @GetMapping("/users/username/{username}")
    @Operation(summary = "Get User by Username", description = "Returns user information based on the provided username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "User not found.")))
    })
    public ResponseEntity<UserResponseDTO> getUserByUsername(
            @PathVariable("username")
            @Parameter(description = "Username of the user to be retrieved", required = true, example = "john_doe") String username) {
        UserResponseDTO userRequestDTO = userService.getUserByUsername(username);
        return new ResponseEntity<>(userRequestDTO, HttpStatus.OK);
    }

    @GetMapping("/tasks/user/{userId}")
    @Operation(summary = "Get Tasks by User ID", description = "Returns all tasks associated with the provided user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User or tasks not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "No tasks found for user.")))
    })
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUserId(
            @PathVariable("userId")
            @Parameter(
                    description = "ID of the user whose tasks are to be retrieved", required = true, example = "1") Long userId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByUserId(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // Task management

    @PostMapping("/tasks")
    @Operation(summary = "Create Task", description = "Creates a new task.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task successfully created.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Invalid task data.")))
    })
    public ResponseEntity<TaskResponseDTO> createTask(
            @RequestBody
            @Parameter(description = "Task data for the new task", required = true) TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO createdTask = taskService.createTask(taskRequestDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{id}")
    @Operation(summary = "Get Task by ID", description = "Returns task information based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Task not found.")))
    })
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @PathVariable("id")
            @Parameter(description = "ID of the task to be retrieved", required = true, example = "1") Long id) {
        TaskResponseDTO taskById = taskService.getTaskById(id);
        return new ResponseEntity<>(taskById, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    @Operation(summary = "Get All Tasks", description = "Returns all tasks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDTO.class)
                    ))
    })
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    @Operation(summary = "Update Task", description = "Updates an existing task.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully updated.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Task not found.")))
    })
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable("id")
            @Parameter(description = "ID of the task to be updated", required = true, example = "1") Long taskId,
            @RequestBody
            @Parameter(description = "Updated task data", required = true) TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO updatedTask = taskService.updateTask(taskId, taskRequestDTO);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    @Operation(summary = "Delete Task", description = "Deletes an existing task.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task successfully deleted."),
            @ApiResponse(responseCode = "404", description = "Task not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Task not found.")))
    })
    public ResponseEntity<Void> deleteTask(
            @PathVariable("id")
            @Parameter(description = "ID of the task to be deleted", required = true, example = "1") Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tasks/title/{title}")
    @Operation(summary = "Get Task by Title", description = "Returns task information based on the provided title.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Task not found.")))
    })
    public ResponseEntity<TaskResponseDTO> getTaskByTitle(
            @PathVariable("title")
            @Parameter(description = "Title of the task to be retrieved", required = true, example = "Sample Task") String title) {
        TaskResponseDTO taskResponseDTO = taskService.getTaskByTitle(title);
        return new ResponseEntity<>(taskResponseDTO, HttpStatus.OK);
    }
}
