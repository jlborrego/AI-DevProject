package com.example.service;

import com.example.exception.InvalidTaskException;
import com.example.exception.NotFoundException;
import com.example.model.Task;
import com.example.model.TaskRequest;
import com.example.model.TaskStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskService {
    private final Map<Integer, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public List<Task> findAll() {
        return List.copyOf(tasks.values());
    }

    public Optional<Task> findById(int id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public Task create(TaskRequest request) {
        validateRequest(request);
        int id = nextId.getAndIncrement();
        String status = TaskStatus.from(request.status()).value();
        Task task = new Task(id, request.title().strip(), request.description().strip(), status);
        tasks.put(id, task);
        return task;
    }

    public Task update(int id, TaskRequest request) {
        validateRequest(request);
        if (!tasks.containsKey(id)) {
            throw new NotFoundException("Task not found: " + id);
        }
        String status = TaskStatus.from(request.status()).value();
        Task task = new Task(id, request.title().strip(), request.description().strip(), status);
        tasks.put(id, task);
        return task;
    }

    public void delete(int id) {
        Task removed = tasks.remove(id);
        if (removed == null) {
            throw new NotFoundException("Task not found: " + id);
        }
    }

    public void validateRequest(TaskRequest request) {
        if (request == null) {
            throw new InvalidTaskException("Task payload is required");
        }
        if (isBlank(request.title())) {
            throw new InvalidTaskException("Task title is required");
        }
        if (isBlank(request.description())) {
            throw new InvalidTaskException("Task description is required");
        }
        try {
            TaskStatus.from(request.status());
        } catch (IllegalArgumentException ex) {
            throw new InvalidTaskException(ex.getMessage());
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
