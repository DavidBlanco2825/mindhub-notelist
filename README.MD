# NoteList Application

## Authors
- [David Fernando Blanco](https://github.com/DavidBlanco2825)

## Overview

The **NoteList** application is a task management tool that allows users to create, view, update, and delete tasks. Each task can be associated with a user and includes attributes like title, description, and status. The application is built with a Spring Boot backend and provides a RESTful API for task management.

## Features

- **User Management**: Create, view, update, and delete users.
- **Task Management**: Create, view, update, and delete tasks.
- **Task Status**: Track tasks by their status (Pending, In Progress, Completed).
- **Search and Filter**: Retrieve tasks by title, user, or status.
- **API Documentation**: Comprehensive API documentation with Swagger.

## Technologies Used

- **Java**: Programming language for the backend.
- **Spring Boot**: Framework for building the RESTful API.
- **Hibernate**: ORM for database interaction.
- **Swagger**: API documentation and testing.
- **Maven**: Dependency management and build tool.

## Getting Started

### Prerequisites

- **Java 17** or later
- **Maven 3.6** or later
- **Git** (for version control)

### Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/notelist.git
    cd notelist
    ```

2. **Build the project**:
    ```bash
    mvn clean install
    ```

3. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

4. **Access the application**:
    - The API will be available at `http://localhost:8080/api`.
    - Swagger API documentation can be accessed at `http://localhost:8080/swagger-ui.html`.

## API Endpoints

### User Endpoints

- **`GET /api/users`**: Retrieve all users.
- **`GET /api/users/{id}`**: Retrieve a user by ID.
- **`POST /api/users`**: Create a new user.
- **`PUT /api/users/{id}`**: Update a user.
- **`DELETE /api/users/{id}`**: Delete a user.

### Task Endpoints

- **`GET /api/tasks`**: Retrieve all tasks.
- **`GET /api/tasks/{id}`**: Retrieve a task by ID.
- **`POST /api/tasks`**: Create a new task.
- **`PUT /api/tasks/{id}`**: Update a task.
- **`DELETE /api/tasks/{id`}**: Delete a task.
- **`GET /api/tasks/title/{title}`**: Retrieve tasks by title.
- **`GET /api/tasks/user/{userId}`**: Retrieve tasks by user ID.
- **`GET /api/tasks/status/{status}`**: Retrieve tasks by status.
