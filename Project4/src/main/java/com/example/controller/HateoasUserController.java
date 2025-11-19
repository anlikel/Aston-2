package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.HateoasService;
import com.example.service.UserService;
import com.example.swagger.CreateUserApiResponse;
import com.example.swagger.DeleteUserApiResponse;
import com.example.swagger.GetAllUsersApiResponse;
import com.example.swagger.GetUserApiResponse;
import com.example.swagger.UpdateUserApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

/**
 * REST контроллер для управления пользователями.
 * Предоставляет endpoints для операций CRUD (Create, Read, Update, Delete) с пользователями.
 * Обрабатывает HTTP запросы и возвращает соответствующие HTTP статусы и DTO объекты.
 */
@RestController
@RequestMapping("/api/hateoasusers")
@Tag(name = "HateoasUserController", description = "API для управления пользователями с поддержкой HATEOAS")
public class HateoasUserController {

    private final HateoasService hateoasService;

    /**
     * Конструктор для внедрения зависимости UserService.
     *
     * @param hateoasService сервис для бизнес-логики работы с пользователями с применнием open-api
     */
    public HateoasUserController(HateoasService hateoasService) {
        this.hateoasService = hateoasService;
    }

    /**
     * Создает нового пользователя на основе предоставленных данных.
     *
     * @param userDto DTO объект с данными для создания пользователя
     * @return ResponseEntity с UserDto
     */
    @PostMapping
    @CreateUserApiResponse
    public ResponseEntity<EntityModel<UserDto>> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(hateoasService.getEntityModelWithCreateUser(userDto));
    }

    /**
     * Получает пользователя по указанному идентификатору.
     *
     * @param id идентификатор пользователя
     * @return ResponseEntity с данными пользователя (200 OK) или сообщением об ошибке (404 Not Found)
     */
    @GetMapping("/{id}")
    @GetUserApiResponse
    public ResponseEntity<EntityModel<UserDto>> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(hateoasService.getEntityModelWithGetUser(id));
    }

    /**
     * Получает список всех пользователей в системе.
     *
     * @return список DTO объектов всех пользователей (200 OK)
     */
    @GetMapping
    @GetAllUsersApiResponse
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(hateoasService.getEntityModelWithGetAllUsers());
    }

    /**
     * Обновляет данные пользователя с указанным идентификатором.
     *
     * @param id            идентификатор пользователя для обновления
     * @param updateUserDto DTO объект с обновленными данными пользователя
     * @return ResponseEntity с обновленными данными пользователя (200 OK) или сообщением об ошибке (404 Not Found)
     */
    @PutMapping("/{id}")
    @UpdateUserApiResponse
    public ResponseEntity<EntityModel<UserDto>> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto updateUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(hateoasService.getEntityModelWithUpdateUser(updateUserDto, id));
    }

    /**
     * Удаляет пользователя с указанным идентификатором.
     *
     * @param id идентификатор пользователя для удаления
     * @return ResponseEntity с подтверждением удаления (200 OK) или сообщением об ошибке (404 Not Found)
     */
    @DeleteMapping("/{id}")
    @DeleteUserApiResponse
    public ResponseEntity<EntityModel<UserDto>> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(hateoasService.getEntityModelWithDeleteUser(id));
    }
}
