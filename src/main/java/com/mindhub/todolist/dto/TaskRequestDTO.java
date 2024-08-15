package com.mindhub.todolist.dto;

import com.mindhub.todolist.entity.TaskStatus;

public class TaskRequestDTO {

    private String title;
    private String description;
    private TaskStatus status;
    private Long userId;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String title, String description, TaskStatus status, Long userId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
