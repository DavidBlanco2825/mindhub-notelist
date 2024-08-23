package com.mindhub.todolist.controller;

import com.mindhub.todolist.dto.TaskRequestDTO;
import com.mindhub.todolist.dto.TaskResponseDTO;
import com.mindhub.todolist.entity.TaskStatus;
import com.mindhub.todolist.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void createTask_shouldReturnCreatedTask_whenValidInput() throws Exception {
        // Arrange
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setTitle("Task 1");
        taskRequestDTO.setDescription("Description 1");
        taskRequestDTO.setStatus(TaskStatus.PENDING);

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setTitle("Task 1");
        taskResponseDTO.setDescription("Description 1");
        taskResponseDTO.setStatus(TaskStatus.PENDING);

        when(taskService.createTask(any(TaskRequestDTO.class))).thenReturn(taskResponseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Task 1\",\"description\":\"Description 1\",\"status\":\"PENDING\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.description").value("Description 1"));
    }


    @Test
    void getTaskById_shouldReturnTask_whenTaskExists() throws Exception {
        // Arrange
        Long taskId = 1L;
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(); // Initialize with appropriate values
        taskResponseDTO.setTitle("Task 1");

        when(taskService.getTaskById(taskId)).thenReturn(taskResponseDTO);

        // Act & Assert
        mockMvc.perform(get("/api/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Task 1"));
    }

    @Test
    void getAllTasks_shouldReturnListOfTasks() throws Exception {
        // Arrange
        TaskResponseDTO task1 = new TaskResponseDTO(); // Initialize with appropriate values
        TaskResponseDTO task2 = new TaskResponseDTO(); // Initialize with appropriate values
        task1.setTitle("Task 1");
        task2.setTitle("Task 2");

        when(taskService.getAllTasks()).thenReturn(List.of(task1, task2));

        // Act & Assert
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    void updateTask_shouldReturnUpdatedTask_whenTaskExists() throws Exception {
        // Arrange
        Long taskId = 1L;
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(); // Initialize with appropriate values
        taskRequestDTO.setTitle("Task 1");
        taskRequestDTO.setDescription("Description 1");
        taskRequestDTO.setStatus(TaskStatus.PENDING);

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(); // Initialize with appropriate values
        taskResponseDTO.setTitle("Updated Task");
        taskResponseDTO.setDescription("Updated Description");
        taskResponseDTO.setStatus(TaskStatus.IN_PROGRESS);

        when(taskService.updateTask(eq(taskId), any(TaskRequestDTO.class))).thenReturn(taskResponseDTO);

        // Act & Assert
        mockMvc.perform(put("/api/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Task\",\"description\":\"Updated Description\",\"status\":\"IN_PROGRESS\"}")) // Example JSON content
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    void deleteTask_shouldReturnNoContent_whenTaskExists() throws Exception {
        // Arrange
        Long taskId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/{id}", taskId))
                .andExpect(status().isNoContent());
    }
}