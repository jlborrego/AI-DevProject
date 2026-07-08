package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TaskPageController {
    
    // Serve the React SPA
    @GetMapping("/")
    public String dashboard() {
        return "forward:/index.html";
    }
    
    // Catch-all for SPA routing - serve index.html for client-side routing
    @GetMapping("/{path:^(?!api|health|static|assets).*}")
    public String catchAll() {
        return "forward:/index.html";
    }

    @GetMapping("/health")
    @ResponseBody
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}

