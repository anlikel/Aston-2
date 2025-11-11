package com.example.controller;

import com.example.dto.CreateUserDto;
import com.example.dto.UserMapper;
import com.example.entities.UserEntity;
import com.example.exceptions.MyCustomException;
import com.example.service.FakeKafkaUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fakeusers")
public class FakeKafkaUserController {

    private final FakeKafkaUserService userService;

    /**
     * Конструктор для внедрения зависимости UserService.
     *
     * @param userService сервис для бизнес-логики работы с пользователями
     */
    public FakeKafkaUserController(FakeKafkaUserService userService) {
        this.userService = userService;
    }

    /**
     * Создает нового пользователя на основе предоставленных данных.
     *
     * @param createUserDto DTO объект с данными для создания пользователя
     * @return ResponseEntity с созданным пользователем (201 Created) или сообщением об ошибке (409 Conflict)
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto) {
        try {
            UserEntity user = UserMapper.toEntity(createUserDto);
            UserEntity savedUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toGetUserDto(savedUser));
        } catch (MyCustomException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Удаляет пользователя с указанным идентификатором.
     *
     * @param id идентификатор пользователя для удаления
     * @return ResponseEntity с подтверждением удаления (200 OK) или сообщением об ошибке (404 Not Found)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body("deleted");
        } catch (MyCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
