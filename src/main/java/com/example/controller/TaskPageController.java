package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TaskPageController {
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "TaskFlow cockpit");
        return "dashboard";
    }

    @GetMapping("/health")
    @ResponseBody
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
