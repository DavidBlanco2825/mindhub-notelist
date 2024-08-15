package com.mindhub.todolist.controller;

import com.mindhub.todolist.dto.TaskRequestDTO;
import com.mindhub.todolist.dto.TaskResponseDTO;
import com.mindhub.todolist.entity.TaskStatus;
import com.mindhub.todolist.service.TaskService;
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
@RequestMapping("/api/tasks")
@Tag(name = "TaskController", description = "Operations related to task management")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
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

    @GetMapping("/{id}")
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

    @GetMapping
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

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
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

    @GetMapping("/title/{title}")
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

    @GetMapping("/user/{userId}")
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

    @GetMapping("/exists/title/{title}")
    @Operation(summary = "Check if Task Title Exists", description = "Checks if a task with the given title exists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task title existence successfully checked.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "boolean", example = "true"))),
    })
    public ResponseEntity<Boolean> checkIfTaskTitleExists(
            @PathVariable("title")
            @Parameter(description = "Title of the task to be checked", required = true, example = "Sample Task") String title) {
        boolean exists = taskService.checkIfTaskTitleExists(title);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @GetMapping("/count/status/{status}")
    @Operation(summary = "Count Tasks by Status", description = "Counts the number of tasks with the given status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count successfully returned.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "integer", example = "5"))),
    })
    public ResponseEntity<Long> countTasksByStatus(
            @PathVariable("status")
            @Parameter(description = "Status of tasks to be counted", required = true, example = "PENDING") TaskStatus status) {
        long count = taskService.countTasksByStatus(status);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
