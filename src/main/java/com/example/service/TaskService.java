package com.example.service;

import com.example.exception.InvalidTaskException;
import com.example.exception.NotFoundException;
import com.example.model.Task;
import com.example.model.TaskRequest;
import com.example.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {
    private static final String LOG_PREFIX = "[TaskMaster]";
    private static final String TASK_PAYLOAD_REQUIRED = "Task payload is required";
    private static final String TASK_TITLE_REQUIRED = "Task title is required";
    private static final String TASK_DESCRIPTION_REQUIRED = "Task description is required";

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
        log("Creating task with id " + id);
        return saveTask(id, request);
    }

    public Task update(int id, TaskRequest request) {
        validateRequest(request);
        if (!tasks.containsKey(id)) {
            throw new NotFoundException("Task not found: " + id);
        }
        log("Updating task with id " + id);
        return saveTask(id, request);
    }

    public void delete(int id) {
        Task removed = tasks.remove(id);
        if (removed == null) {
            throw new NotFoundException("Task not found: " + id);
        }
        log("Deleted task with id " + id);
    }

    public void validateRequest(TaskRequest request) {
        if (request == null) {
            log("Validation failed: task payload is required");
            throw new InvalidTaskException(TASK_PAYLOAD_REQUIRED);
        }
        if (isBlank(request.title())) {
            log("Validation failed: task title is required");
            throw new InvalidTaskException(TASK_TITLE_REQUIRED);
        }
        if (isBlank(request.description())) {
            log("Validation failed: task description is required");
            throw new InvalidTaskException(TASK_DESCRIPTION_REQUIRED);
        }
        try {
            TaskStatus.from(request.status());
        } catch (IllegalArgumentException ex) {
            log("Validation failed: invalid status '" + request.status() + "'");
            throw new InvalidTaskException(ex.getMessage());
        }
    }

    private Task saveTask(int id, TaskRequest request) {
        TaskStatus status = TaskStatus.from(request.status());
        Task task = new Task(
            id,
            normalizeValue(request.title()),
            normalizeValue(request.description()),
            status.value()
        );
        tasks.put(id, task);
        return task;
    }

    private String normalizeValue(String value) {
        return value == null ? "" : value.strip();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private void log(String message) {
        System.out.println(LOG_PREFIX + " " + message);
    }
}
