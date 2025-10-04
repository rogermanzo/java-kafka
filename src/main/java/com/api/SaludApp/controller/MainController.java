package com.api.SaludApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String home() {
        return "SaludApp - Microservices Architecture";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
