package com.example;

import com.example.controller.TaskController;
import com.example.service.TaskService;
import io.javalin.Javalin;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        TaskController taskController = new TaskController(taskService);

        Javalin app = Javalin.create(config -> {
            config.routes.get("/health", ctx -> {
                ctx.contentType("application/json");
                ctx.json(Map.of("status", "UP"));
            });
            taskController.registerRoutes(config.routes);
        });

        app.start(7000);
        System.out.println("Server started on http://localhost:7000");
    }
}
