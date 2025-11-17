package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * REST контроллер для управления пользователями.
 * Предоставляет endpoints для операций CRUD (Create, Read, Update, Delete) с пользователями.
 * Обрабатывает HTTP запросы и возвращает соответствующие HTTP статусы и DTO объекты.
 */
@RestController
@RequestMapping("/api/hateoasusers")
public class HateoasUserController {

    private final UserService userService;

    /**
     * Конструктор для внедрения зависимости UserService.
     *
     * @param userService сервис для бизнес-логики работы с пользователями
     */
    public HateoasUserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Создает нового пользователя на основе предоставленных данных.
     *
     * @param userDto DTO объект с данными для создания пользователя
     * @return ResponseEntity с UserDto
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(userDto));
    }

    /**
     * Получает пользователя по указанному идентификатору.
     *
     * @param id идентификатор пользователя
     * @return ResponseEntity с данными пользователя (200 OK) или сообщением об ошибке (404 Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    /**
     * Получает список всех пользователей в системе.
     *
     * @return список DTO объектов всех пользователей (200 OK)
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Обновляет данные пользователя с указанным идентификатором.
     *
     * @param id            идентификатор пользователя для обновления
     * @param updateUserDto DTO объект с обновленными данными пользователя
     * @return ResponseEntity с обновленными данными пользователя (200 OK) или сообщением об ошибке (404 Not Found)
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto updateUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(updateUserDto, id));
    }

    /**
     * Удаляет пользователя с указанным идентификатором.
     *
     * @param id идентификатор пользователя для удаления
     * @return ResponseEntity с подтверждением удаления (200 OK) или сообщением об ошибке (404 Not Found)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(id));
    }
}
