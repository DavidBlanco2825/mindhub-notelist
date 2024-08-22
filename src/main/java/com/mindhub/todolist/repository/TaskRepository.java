package com.mindhub.todolist.repository;

import com.mindhub.todolist.entity.Task;
import com.mindhub.todolist.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String title);

    List<Task> findByUserEntityId(Long userId);

    List<Task> findByUserEntityUsername(String username);

    List<Task> findByUserEntityUsernameAndStatus(String username, TaskStatus status);
}
