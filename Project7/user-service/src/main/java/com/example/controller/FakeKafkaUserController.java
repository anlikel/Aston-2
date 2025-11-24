package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.FakeKafkaUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки операций с пользователями через фейковый Kafka сервис.
 * Предоставляет REST API для создания и удаления пользователей с использованием фейковой Kafka.
 */
@RestController
@RequestMapping("/api/fakeusers")
public class FakeKafkaUserController {

    private final FakeKafkaUserService userService;

    /**
     * Конструктор для внедрения зависимости FakeKafkaUserService.
     *
     * @param userService сервис для бизнес-логики работы с пользователями через фейковый Kafka
     */
    public FakeKafkaUserController(FakeKafkaUserService userService) {
        this.userService = userService;
    }

    /**
     * Создает нового пользователя на основе предоставленных данных.
     * Использует фейковый Kafka сервис для отправки событий о создании пользователя.
     *
     * @param userDto DTO объект с данными для создания пользователя
     * @return ResponseEntity с UserDto созданного пользователя или сообщением об ошибке
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(userDto));
    }

    /**
     * Удаляет пользователя с указанным идентификатором.
     * Использует фейковый Kafka сервис для отправки событий об удалении пользователя.
     *
     * @param id идентификатор пользователя для удаления
     * @return ResponseEntity с UserDto удаленного пользователя или сообщением об ошибке
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(id));
    }
}