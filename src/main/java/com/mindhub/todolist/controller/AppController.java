package com.mindhub.todolist.controller;

import com.mindhub.todolist.dto.TaskResponseDTO;
import com.mindhub.todolist.dto.UserDTO;
import com.mindhub.todolist.service.TaskService;
import com.mindhub.todolist.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "AppController", description = "Operations that user's of the application can execute")
public class AppController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public AppController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
        UserDTO userDTO = userService.getUserByUsername(authentication.getName());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getUserTasks(Authentication authentication) {
        List<TaskResponseDTO> tasks = taskService.getTasksByUsername(authentication.getName());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
