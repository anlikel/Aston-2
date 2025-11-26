package com.example.circuitbreaker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/user-service")
    public ResponseEntity<Map<String, Object>> userServiceFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User service is temporarily unavailable. Please try again later.");
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("fallback", true);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @GetMapping("/swagger")
    public ResponseEntity<Map<String, Object>> swaggerFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "API documentation is temporarily unavailable.");
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("fallback", true);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
