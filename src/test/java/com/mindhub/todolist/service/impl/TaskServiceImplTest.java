package com.mindhub.todolist.service.impl;

import com.mindhub.todolist.dto.TaskRequestDTO;
import com.mindhub.todolist.dto.TaskResponseDTO;
import com.mindhub.todolist.entity.Task;
import com.mindhub.todolist.entity.TaskStatus;
import com.mindhub.todolist.exception.ResourceNotFoundException;
import com.mindhub.todolist.mapper.TaskMapper;
import com.mindhub.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_shouldSaveTaskAndReturnResponseDTO() {
        // Arrange
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(); // Initialize with appropriate values
        Task taskEntity = new Task(); // Initialize with appropriate values
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(); // Initialize with appropriate values

        when(taskMapper.toEntity(taskRequestDTO)).thenReturn(taskEntity);
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);
        when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDTO);

        // Act
        TaskResponseDTO result = taskService.createTask(taskRequestDTO);

        // Assert
        assertEquals(taskResponseDTO, result);
        verify(taskMapper).toEntity(taskRequestDTO);
        verify(taskRepository).save(taskEntity);
        verify(taskMapper).toResponseDto(taskEntity);
    }

    @Test
    void getTaskById_shouldReturnTaskResponseDTO_whenTaskExists() {
        // Arrange
        Long taskId = 1L;
        Task taskEntity = new Task(); // Initialize with appropriate values
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(); // Initialize with appropriate values

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDTO);

        // Act
        TaskResponseDTO result = taskService.getTaskById(taskId);

        // Assert
        assertEquals(taskResponseDTO, result);
        verify(taskRepository).findById(taskId);
        verify(taskMapper).toResponseDto(taskEntity);
    }

    @Test
    void getTaskById_shouldThrowResourceNotFoundException_whenTaskDoesNotExist() {
        // Arrange
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(taskId));
        verify(taskRepository).findById(taskId);
    }

    @Test
    void getAllTasks_shouldReturnListOfTaskResponseDTO() {
        // Arrange
        Task taskEntity = new Task(); // Initialize with appropriate values
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(); // Initialize with appropriate values
        List<Task> taskList = Collections.singletonList(taskEntity);
        List<TaskResponseDTO> taskResponseDTOList = Collections.singletonList(taskResponseDTO);

        when(taskRepository.findAll()).thenReturn(taskList);
        when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDTO);

        // Act
        List<TaskResponseDTO> result = taskService.getAllTasks();

        // Assert
        assertEquals(taskResponseDTOList, result);
        verify(taskRepository).findAll();
        verify(taskMapper).toResponseDto(taskEntity);
    }

    @Test
    void updateTask_shouldUpdateAndReturnTaskResponseDTO_whenTaskExists() {
        // Arrange
        Long taskId = 1L;
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(); // Initialize with appropriate values
        Task existingTask = new Task(); // Initialize with appropriate values
        Task updatedTask = new Task(); // Initialize with appropriate values
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(); // Initialize with appropriate values

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(updatedTask);
        when(taskMapper.toResponseDto(updatedTask)).thenReturn(taskResponseDTO);

        // Act
        TaskResponseDTO result = taskService.updateTask(taskId, taskRequestDTO);

        // Assert
        assertEquals(taskResponseDTO, result);
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(existingTask);
        verify(taskMapper).toResponseDto(updatedTask);
    }

    @Test
    void updateTask_shouldThrowResourceNotFoundException_whenTaskDoesNotExist() {
        // Arrange
        Long taskId = 1L;
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(); // Initialize with appropriate values

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskService.updateTask(taskId, taskRequestDTO));
        verify(taskRepository).findById(taskId);
    }

    @Test
    void deleteTask_shouldDeleteTask_whenTaskExists() {
        // Arrange
        Long taskId = 1L;

        when(taskRepository.existsById(taskId)).thenReturn(true);

        // Act
        taskService.deleteTask(taskId);

        // Assert
        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void deleteTask_shouldThrowResourceNotFoundException_whenTaskDoesNotExist() {
        // Arrange
        Long taskId = 1L;

        when(taskRepository.existsById(taskId)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskService.deleteTask(taskId));
        verify(taskRepository).existsById(taskId);
    }

    @Test
    void getTaskByTitle_shouldReturnTaskResponseDTO_whenTaskExists() {
        // Arrange
        String taskTitle = "Test Task";
        Task taskEntity = new Task(); // Initialize with appropriate values
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(); // Initialize with appropriate values

        when(taskRepository.findByTitle(taskTitle)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDTO);

        // Act
        TaskResponseDTO result = taskService.getTaskByTitle(taskTitle);

        // Assert
        assertEquals(taskResponseDTO, result);
        verify(taskRepository).findByTitle(taskTitle);
        verify(taskMapper).toResponseDto(taskEntity);
    }

    @Test
    void getTaskByTitle_shouldThrowResourceNotFoundException_whenTaskDoesNotExist() {
        // Arrange
        String taskTitle = "Nonexistent Task";

        when(taskRepository.findByTitle(taskTitle)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskByTitle(taskTitle));
        verify(taskRepository).findByTitle(taskTitle);
    }

    @Test
    void getTasksByUserId_shouldReturnListOfTaskResponseDTO() {
        // Arrange
        Long userId = 1L;
        Task taskEntity = new Task(); // Initialize with appropriate values
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(); // Initialize with appropriate values
        List<Task> taskList = Collections.singletonList(taskEntity);
        List<TaskResponseDTO> taskResponseDTOList = Collections.singletonList(taskResponseDTO);

        when(taskRepository.findByUserEntityId(userId)).thenReturn(taskList);
        when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDTO);

        // Act
        List<TaskResponseDTO> result = taskService.getTasksByUserId(userId);

        // Assert
        assertEquals(taskResponseDTOList, result);
        verify(taskRepository).findByUserEntityId(userId);
        verify(taskMapper).toResponseDto(taskEntity);
    }

    @Test
    void getTasksByUsername_shouldReturnListOfTaskResponseDTO() {
        // Arrange
        String username = "testuser";
        Task taskEntity = new Task(); // Initialize with appropriate values
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(); // Initialize with appropriate values
        List<Task> taskList = Collections.singletonList(taskEntity);
        List<TaskResponseDTO> taskResponseDTOList = Collections.singletonList(taskResponseDTO);

        when(taskRepository.findByUserEntityUsername(username)).thenReturn(taskList);
        when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDTO);

        // Act
        List<TaskResponseDTO> result = taskService.getTasksByUsername(username);

        // Assert
        assertEquals(taskResponseDTOList, result);
        verify(taskRepository).findByUserEntityUsername(username);
        verify(taskMapper).toResponseDto(taskEntity);
    }

    @Test
    void getTasksByUsernameAndStatus_shouldReturnListOfTaskResponseDTO() {
        // Arrange
        String username = "testuser";
        TaskStatus status = TaskStatus.PENDING;
        Task taskEntity = new Task(); // Initialize with appropriate values
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(); // Initialize with appropriate values
        List<Task> taskList = Collections.singletonList(taskEntity);
        List<TaskResponseDTO> taskResponseDTOList = Collections.singletonList(taskResponseDTO);

        when(taskRepository.findByUserEntityUsernameAndStatus(username, status)).thenReturn(taskList);
        when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDTO);

        // Act
        List<TaskResponseDTO> result = taskService.getTasksByUsernameAndStatus(username, status);

        // Assert
        assertEquals(taskResponseDTOList, result);
        verify(taskRepository).findByUserEntityUsernameAndStatus(username, status);
        verify(taskMapper).toResponseDto(taskEntity);
    }
}