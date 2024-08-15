package com.mindhub.todolist.mapper;

import com.mindhub.todolist.dto.TaskRequestDTO;
import com.mindhub.todolist.dto.TaskResponseDTO;
import com.mindhub.todolist.entity.Task;
import com.mindhub.todolist.entity.UserEntity;
import com.mindhub.todolist.exception.ResourceNotFoundException;
import com.mindhub.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mindhub.todolist.config.Constans.USER_NOT_FOUND_ID;

@Component
public class TaskMapper {

    private final UserRepository userRepository;

    @Autowired
    public TaskMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public TaskResponseDTO toResponseDto(Task task) {
        if (task == null) {
            return null;
        }

        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }

    public Task toEntity(TaskRequestDTO taskRequestDTO) {
        if (taskRequestDTO == null) {
            return null;
        }

        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setStatus(taskRequestDTO.getStatus());

        // Fetch the user entity based on the userId from TaskRequestDTO
        if (taskRequestDTO.getUserId() != null) {
            UserEntity userEntity = userRepository.findById(taskRequestDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_ID + taskRequestDTO.getUserId()));
            task.setUserEntity(userEntity);
        }

        return task;
    }
}
