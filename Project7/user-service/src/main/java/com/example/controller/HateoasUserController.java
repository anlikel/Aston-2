package com.example.controller;

import com.example.dto.UserDto;
import com.example.hateoas.HateoasServiceImpl;
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
 * Также контроллер реализует функции библиотеки HATEOAS и предоставляет описание
 * своих функций через open api.
 *
 * <p><strong>Кастомные аннотации:</strong>
 * <ul>
 *   <li>{@link CreateUserApiResponse} - документирование ответов для создания пользователя</li>
 *   <li>{@link GetUserApiResponse} - документирование ответов для получения пользователя</li>
 *   <li>{@link GetAllUsersApiResponse} - документирование ответов для получения всех пользователей</li>
 *   <li>{@link UpdateUserApiResponse} - документирование ответов для обновления пользователя</li>
 *   <li>{@link DeleteUserApiResponse} - документирование ответов для удаления пользователя</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/hateoasusers")
@Tag(name = "HateoasUserController", description = "API для управления пользователями с поддержкой HATEOAS")
public class HateoasUserController {

    private final HateoasServiceImpl hateoasServiceImpl;

    /**
     * Конструктор для внедрения зависимости HateoasServiceImpl.
     *
     * @param hateoasServiceImpl сервис для бизнес-логики работы с пользователями с применением open-api
     */
    public HateoasUserController(HateoasServiceImpl hateoasServiceImpl) {
        this.hateoasServiceImpl = hateoasServiceImpl;
    }

    /**
     * Создает нового пользователя на основе предоставленных данных.
     * Используется кастомная аннотация {@link CreateUserApiResponse} для документирования ответов API.
     *
     * @param userDto DTO объект с данными для создания пользователя
     * @return ResponseEntity с UserDto
     * @see CreateUserApiResponse
     */
    @PostMapping
    @CreateUserApiResponse
    public ResponseEntity<EntityModel<UserDto>> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(hateoasServiceImpl.getEntityModelWithCreateUser(userDto));
    }

    /**
     * Получает пользователя по указанному идентификатору.
     * Используется кастомная аннотация {@link GetUserApiResponse} для документирования ответов API.
     *
     * @param id идентификатор пользователя
     * @return ResponseEntity с данными пользователя (200 OK) или сообщением об ошибке (404 Not Found)
     * @see GetUserApiResponse
     */
    @GetMapping("/{id}")
    @GetUserApiResponse
    public ResponseEntity<EntityModel<UserDto>> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(hateoasServiceImpl.getEntityModelWithGetUser(id));
    }

    /**
     * Получает список всех пользователей в системе.
     * Используется кастомная аннотация {@link GetAllUsersApiResponse} для документирования ответов API.
     *
     * @return список DTO объектов всех пользователей (200 OK)
     * @see GetAllUsersApiResponse
     */
    @GetMapping
    @GetAllUsersApiResponse
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(hateoasServiceImpl.getEntityModelWithGetAllUsers());
    }

    /**
     * Обновляет данные пользователя с указанным идентификатором.
     * Используется кастомная аннотация {@link UpdateUserApiResponse} для документирования ответов API.
     *
     * @param id            идентификатор пользователя для обновления
     * @param updateUserDto DTO объект с обновленными данными пользователя
     * @return ResponseEntity с обновленными данными пользователя (200 OK) или сообщением об ошибке (404 Not Found)
     * @see UpdateUserApiResponse
     */
    @PutMapping("/{id}")
    @UpdateUserApiResponse
    public ResponseEntity<EntityModel<UserDto>> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto updateUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(hateoasServiceImpl.getEntityModelWithUpdateUser(updateUserDto, id));
    }

    /**
     * Удаляет пользователя с указанным идентификатором.
     * Используется кастомная аннотация {@link DeleteUserApiResponse} для документирования ответов API.
     *
     * @param id идентификатор пользователя для удаления
     * @return ResponseEntity с подтверждением удаления (200 OK) или сообщением об ошибке (404 Not Found)
     * @see DeleteUserApiResponse
     */
    @DeleteMapping("/{id}")
    @DeleteUserApiResponse
    public ResponseEntity<EntityModel<UserDto>> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(hateoasServiceImpl.getEntityModelWithDeleteUser(id));
    }
}