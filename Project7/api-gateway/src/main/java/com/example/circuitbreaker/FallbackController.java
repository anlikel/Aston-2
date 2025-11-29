package com.example.circuitbreaker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер для обработки fallback-ответов при срабатывании Circuit Breaker
 *
 * <p>Этот контроллер предоставляет резервные endpoints, которые вызываются когда
 * целевые сервисы недоступны или работают с ошибками.</p>
 *
 * @RestController - отмечает класс как Spring MVC контроллер, где каждый метод
 * возвращает данные вместо представления
 * @RequestMapping("/fallback") - базовый путь для всех endpoints в этом контроллере
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    /**
     * Fallback endpoint для user-service
     *
     * <p>Вызывается когда Circuit Breaker для user-service переходит в состояние "open"
     * из-за множественных ошибок или таймаутов</p>
     *
     * @return ResponseEntity с информацией о недоступности сервиса
     * @implNote Возвращает HTTP статус 503 SERVICE_UNAVAILABLE
     * @implNote Включает timestamp для отслеживания времени срабатывания
     * @example Response:
     * {
     * "message": "User service is temporarily unavailable. Please try again later.",
     * "timestamp": 1638291832000,
     * "status": 503,
     * "fallback": true
     * }
     */
    @GetMapping("/user-service")
    public ResponseEntity<Map<String, Object>> userServiceFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User service is temporarily unavailable. Please try again later.");
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("fallback", true);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    /**
     * Fallback endpoint для Swagger/OpenAPI документации
     *
     * <p>Вызывается когда сервис документации недоступен</p>
     *
     * @return ResponseEntity с информацией о недоступности документации
     * @implNote Возвращает HTTP статус 503 SERVICE_UNAVAILABLE
     * @example Response:
     * {
     * "message": "API documentation is temporarily unavailable.",
     * "timestamp": 1638291832000,
     * "status": 503,
     * "fallback": true
     * }
     */
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