package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.UserService;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<EntityModel<UserDto>> createUser(@RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.createUser(userDto);
        EntityModel<UserDto> user = EntityModel.of(savedUserDto);
        Long id = savedUserDto.getId();
        user.add(
                linkTo(methodOn(UserController.class).createUser(userDto)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAllUsers"),
                linkTo(methodOn(UserController.class).getUser(id)).withRel("getUser"),
                linkTo(methodOn(UserController.class).deleteUser(id)).withRel("deleteUser"),
                linkTo(methodOn(UserController.class).updateUser(id, userDto)).withRel("updateUser")
        );
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * Получает пользователя по указанному идентификатору.
     *
     * @param id идентификатор пользователя
     * @return ResponseEntity с данными пользователя (200 OK) или сообщением об ошибке (404 Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDto>> getUser(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        EntityModel<UserDto> user = EntityModel.of(userDto);
        user.add(
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAllUsers"),
                linkTo(methodOn(UserController.class).deleteUser(id)).withRel("deleteUser"),
                linkTo(methodOn(UserController.class).createUser(userDto)).withRel("createUser"),
                linkTo(methodOn(UserController.class).updateUser(id, userDto)).withRel("updateUser")
        );
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * Получает список всех пользователей в системе.
     *
     * @return список DTO объектов всех пользователей (200 OK)
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAllUsers() {
        List<EntityModel<UserDto>> userModels = userService.getAllUsers().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("deleteUser"),
                        linkTo(methodOn(UserController.class).createUser(user)).withRel("createUser"),
                        linkTo(methodOn(UserController.class).updateUser(user.getId(), user)).withRel("updateUser")
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<UserDto>> users = CollectionModel.of(userModels);
        users.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(users);
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
        UserDto user = userService.updateUser(updateUserDto, id);
        EntityModel<UserDto> userModel = EntityModel.of(user);
        userModel.add(
                linkTo(methodOn(UserController.class).updateUser(id, updateUserDto)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAllUsers"),
                linkTo(methodOn(UserController.class).getUser(id)).withRel("getUser"),
                linkTo(methodOn(UserController.class).deleteUser(id)).withRel("deleteUser"),
                linkTo(methodOn(UserController.class).createUser(updateUserDto)).withRel("createUser")
        );
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * Удаляет пользователя с указанным идентификатором.
     *
     * @param id идентификатор пользователя для удаления
     * @return ResponseEntity с подтверждением удаления (200 OK) или сообщением об ошибке (404 Not Found)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<UserDto>> deleteUser(@PathVariable Long id) {
        UserDto userDto = userService.deleteUserById(id);
        EntityModel<UserDto> user = EntityModel.of(userDto);
        user.add(
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAllUsers"),
                linkTo(methodOn(UserController.class).getUser(id)).withRel("getUser"),
                linkTo(methodOn(UserController.class).createUser(userDto)).withRel("createUser"),
                linkTo(methodOn(UserController.class).updateUser(id, userDto)).withRel("updateUser")
        );
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
