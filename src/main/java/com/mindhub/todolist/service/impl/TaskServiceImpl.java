package com.mindhub.todolist.service.impl;

import com.mindhub.todolist.dto.TaskRequestDTO;
import com.mindhub.todolist.dto.TaskResponseDTO;
import com.mindhub.todolist.entity.Task;
import com.mindhub.todolist.entity.TaskStatus;
import com.mindhub.todolist.exception.ResourceNotFoundException;
import com.mindhub.todolist.mapper.TaskMapper;
import com.mindhub.todolist.repository.TaskRepository;
import com.mindhub.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mindhub.todolist.config.Constans.TASK_NOT_FOUND_ID;
import static com.mindhub.todolist.config.Constans.TASK_NOT_FOUND_TITLE;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = taskMapper.toEntity(taskRequestDTO);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponseDto(savedTask);
    }

    @Override
    public TaskResponseDTO getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .map(taskMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(TASK_NOT_FOUND_ID + taskId));
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @Override
    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO taskRequestDTO) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(TASK_NOT_FOUND_ID + taskId));

        existingTask.setTitle(taskRequestDTO.getTitle());
        existingTask.setDescription(taskRequestDTO.getDescription());
        existingTask.setStatus(taskRequestDTO.getStatus());

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toResponseDto(updatedTask);
    }

    @Override
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException(TASK_NOT_FOUND_ID + taskId);
        }
        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskResponseDTO getTaskByTitle(String taskTitle) {
        return taskRepository.findByTitle(taskTitle)
                .map(taskMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException(TASK_NOT_FOUND_TITLE + taskTitle));
    }

    @Override
    public List<TaskResponseDTO> getTasksByUserId(Long userId) {
        return taskRepository.findByUserEntityId(userId)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<TaskResponseDTO> getTasksByUsername(String username) {
        return taskRepository.findByUserEntityUsername(username)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<TaskResponseDTO> getTasksByUsernameAndStatus(String username, TaskStatus status) {
        return taskRepository.findByUserEntityUsernameAndStatus(username, status)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }
}
