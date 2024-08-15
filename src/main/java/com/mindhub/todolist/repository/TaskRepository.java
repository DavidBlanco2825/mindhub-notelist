package com.mindhub.todolist.repository;

import com.mindhub.todolist.entity.Task;
import com.mindhub.todolist.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String title);

    boolean existsByTitle(String title);

    long countByStatus(TaskStatus status);

    List<Task> findByUserEntityId(Long userId);
}
