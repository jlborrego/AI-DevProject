package com.example.controller;

import com.example.exception.InvalidTaskException;
import com.example.exception.NotFoundException;
import com.example.model.Task;
import com.example.model.TaskRequest;
import com.example.service.TaskService;
import io.javalin.config.RoutesConfig;
import io.javalin.http.Context;

public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    public void registerRoutes(RoutesConfig routes) {
        routes.get("/tasks", this::listTasks)
            .get("/tasks/{id}", this::getTaskById)
            .post("/tasks", this::createTask)
            .put("/tasks/{id}", this::updateTask)
            .delete("/tasks/{id}", this::deleteTask)
            .exception(NotFoundException.class, (e, ctx) -> handleNotFound(ctx, e))
            .exception(InvalidTaskException.class, (e, ctx) -> handleBadRequest(ctx, e))
            .exception(Exception.class, (e, ctx) -> handleServerError(ctx, e));
    }

    private void listTasks(Context ctx) {
        ctx.json(service.findAll());
    }

    private void getTaskById(Context ctx) {
        int id = parseId(ctx);
        Task task = service.findById(id).orElseThrow(() -> new NotFoundException("Task not found: " + id));
        ctx.json(task);
    }

    private void createTask(Context ctx) {
        TaskRequest request = ctx.bodyAsClass(TaskRequest.class);
        Task created = service.create(request);
        ctx.status(201).json(created);
    }

    private void updateTask(Context ctx) {
        int id = parseId(ctx);
        TaskRequest request = ctx.bodyAsClass(TaskRequest.class);
        Task updated = service.update(id, request);
        ctx.json(updated);
    }

    private void deleteTask(Context ctx) {
        int id = parseId(ctx);
        service.delete(id);
        ctx.status(204);
    }

    private void handleNotFound(Context ctx, RuntimeException exception) {
        ctx.status(404).json(new ErrorResponse(exception.getMessage()));
    }

    private void handleBadRequest(Context ctx, RuntimeException exception) {
        ctx.status(400).json(new ErrorResponse(exception.getMessage()));
    }

    private void handleServerError(Context ctx, Exception exception) {
        ctx.status(500).json(new ErrorResponse("Internal server error"));
    }

    private int parseId(Context ctx) {
        try {
            return Integer.parseInt(ctx.pathParam("id"));
        } catch (NumberFormatException ex) {
            throw new InvalidTaskException("Invalid task id");
        }
    }

    private static final class ErrorResponse {
        public final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
