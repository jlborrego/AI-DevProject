package com.example.controller;

import com.example.exception.InvalidTaskException;
import com.example.exception.NotFoundException;
import com.example.model.Task;
import com.example.model.TaskRequest;
import com.example.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final String LOG_PREFIX = "[TaskMaster]";
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> listTasks() {
        logRequest("GET", null);
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) {
        int taskId = parseId(id);
        logRequest("GET", taskId);
        return service.findById(taskId)
            .orElseThrow(() -> new NotFoundException("Task not found: " + taskId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody TaskRequest request) {
        logRequest("POST", null);
        return service.create(request);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody TaskRequest request) {
        int taskId = parseId(id);
        logRequest("PUT", taskId);
        return service.update(taskId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String id) {
        int taskId = parseId(id);
        logRequest("DELETE", taskId);
        service.delete(taskId);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFoundException exception) {
        logError("Resource not found", exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(InvalidTaskException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(InvalidTaskException exception) {
        logError("Invalid request", exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        logError("Invalid request", exception);
        return new ErrorResponse("Invalid task id");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerError(Exception exception) {
        logError("Unhandled server error", exception);
        return new ErrorResponse("Internal server error");
    }

    private int parseId(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new InvalidTaskException("Invalid task id");
        }
    }

    private void logRequest(String method, Integer taskId) {
        String path = taskId == null ? "/tasks" : "/tasks/" + taskId;
        log("Handling " + method + " " + path);
    }

    private void log(String message) {
        System.out.println(LOG_PREFIX + " " + message);
    }

    private void logError(String message, Exception exception) {
        System.out.println(LOG_PREFIX + " " + message + ": " + exception.getMessage());
    }

    private record ErrorResponse(String error) {
    }
}
