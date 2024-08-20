package com.mindhub.todolist.service;

import com.mindhub.todolist.dto.TaskRequestDTO;
import com.mindhub.todolist.dto.TaskResponseDTO;
import com.mindhub.todolist.entity.TaskStatus;

import java.util.List;

public interface TaskService {

    // CRUD operations
    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO);

    TaskResponseDTO getTaskById(Long taskId);

    List<TaskResponseDTO> getAllTasks();

    TaskResponseDTO updateTask(Long taskId, TaskRequestDTO taskRequestDTO);

    void deleteTask(Long id);

    // Find by specific attributes
    TaskResponseDTO getTaskByTitle(String title);

    List<TaskResponseDTO> getTasksByUserId(Long userId);

    List<TaskResponseDTO> getTasksByUsername(String username);

    // Utility methods
    boolean checkIfTaskTitleExists(String title);

    long countTasksByStatus(TaskStatus status);
}
