package com.cinemaabyss.proxy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping(value = "/health", produces = "text/plain")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Proxy is healthy");
    }
}



