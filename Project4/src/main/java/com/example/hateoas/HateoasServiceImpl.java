package com.example.hateoas;

import com.example.controller.UserController;
import com.example.dto.UserDto;
import com.example.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Реализация сервиса для работы с HATEOAS (Hypermedia as the Engine of Application State).
 * Предоставляет методы для оборачивания DTO пользователей в HATEOAS-модели с соответствующими ссылками.
 *
 * <p>Данная реализация добавляет HATEOAS-ссылки к сущностям пользователей, позволяя клиентам
 * динамически обнаруживать доступные операции через гипермедиа-ссылки.</p>
 *
 * @implSpec Реализует интерфейс {@link HateoasServiceInterface} для типа {@link UserDto}
 * @see HateoasServiceInterface
 * @see UserDto
 * @see EntityModel
 * @see CollectionModel
 */
@Service
public class HateoasServiceImpl implements HateoasServiceInterface<UserDto, Long> {

    private final UserService userService;

    /**
     * Конструктор для внедрения зависимости UserService.
     *
     * @param userService сервис для работы с данными пользователей
     */
    public HateoasServiceImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * Создает HATEOAS-модель для вновь созданного пользователя.
     * Добавляет ссылки на операции: self, getAllUsers, getUser, deleteUser, updateUser.
     *
     * @param userDto DTO объект с данными созданного пользователя
     * @return EntityModel с данными пользователя и HATEOAS-ссылками
     * @implNote Добавляет ссылки на все основные операции с пользователем
     */
    public EntityModel<UserDto> getEntityModelWithCreateUser(UserDto userDto) {
        UserDto savedUserDto = userService.createUser(userDto);
        EntityModel<UserDto> userModel = EntityModel.of(savedUserDto);
        Long id = savedUserDto.getId();
        userModel.add(
                linkTo(methodOn(UserController.class).createUser(userDto)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAllUsers"),
                linkTo(methodOn(UserController.class).getUser(id)).withRel("getUser"),
                linkTo(methodOn(UserController.class).deleteUser(id)).withRel("deleteUser"),
                linkTo(methodOn(UserController.class).updateUser(id, userDto)).withRel("updateUser")
        );
        return userModel;
    }

    /**
     * Создает HATEOAS-модель для обновленного пользователя.
     * Добавляет ссылки на операции: self, getAllUsers, getUser, deleteUser, createUser.
     *
     * @param updateUserDto DTO объект с обновленными данными пользователя
     * @param id            идентификатор обновленного пользователя
     * @return EntityModel с обновленными данными пользователя и HATEOAS-ссылками
     * @implNote Включает ссылку на создание нового пользователя для удобства навигации
     */
    public EntityModel<UserDto> getEntityModelWithUpdateUser(UserDto updateUserDto, Long id) {
        UserDto user = userService.updateUser(updateUserDto, id);
        EntityModel<UserDto> userModel = EntityModel.of(user);
        userModel.add(
                linkTo(methodOn(UserController.class).updateUser(id, updateUserDto)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAllUsers"),
                linkTo(methodOn(UserController.class).getUser(id)).withRel("getUser"),
                linkTo(methodOn(UserController.class).deleteUser(id)).withRel("deleteUser"),
                linkTo(methodOn(UserController.class).createUser(updateUserDto)).withRel("createUser")
        );
        return userModel;
    }

    /**
     * Создает HATEOAS-модель для удаленного пользователя.
     * Добавляет ссылки на операции: self, getAllUsers, getUser, createUser, updateUser.
     *
     * @param id идентификатор удаленного пользователя
     * @return EntityModel с данными об операции удаления и HATEOAS-ссылками
     * @implNote Возвращает данные удаленного пользователя с ссылками для навигации
     */
    public EntityModel<UserDto> getEntityModelWithDeleteUser(Long id) {
        UserDto userDto = userService.deleteUserById(id);
        EntityModel<UserDto> userModel = EntityModel.of(userDto);
        userModel.add(
                linkTo(methodOn(UserController.class).deleteUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAllUsers"),
                linkTo(methodOn(UserController.class).getUser(id)).withRel("getUser"),
                linkTo(methodOn(UserController.class).createUser(userDto)).withRel("createUser"),
                linkTo(methodOn(UserController.class).updateUser(id, userDto)).withRel("updateUser")
        );
        return userModel;
    }

    /**
     * Создает HATEOAS-модель для пользователя, полученного по идентификатору.
     * Добавляет ссылки на операции: self, getAllUsers, deleteUser, createUser, updateUser.
     *
     * @param id идентификатор пользователя
     * @return EntityModel с данными пользователя и HATEOAS-ссылками
     * @implNote Предоставляет полный набор ссылок для работы с конкретным пользователем
     */
    public EntityModel<UserDto> getEntityModelWithGetUser(Long id) {
        UserDto userDto = userService.getUserById(id);
        EntityModel<UserDto> userModel = EntityModel.of(userDto);
        userModel.add(
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("getAllUsers"),
                linkTo(methodOn(UserController.class).deleteUser(id)).withRel("deleteUser"),
                linkTo(methodOn(UserController.class).createUser(userDto)).withRel("createUser"),
                linkTo(methodOn(UserController.class).updateUser(id, userDto)).withRel("updateUser")
        );
        return userModel;
    }

    /**
     * Создает HATEOAS-модель для коллекции всех пользователей.
     * Каждый пользователь в коллекции получает ссылки на свои операции.
     *
     * @return CollectionModel с коллекцией пользователей и HATEOAS-ссылками
     * @implNote Каждый элемент коллекции содержит ссылки на свои CRUD операции,
     * а сама коллекция имеет self-ссылку
     */
    public CollectionModel<EntityModel<UserDto>> getEntityModelWithGetAllUsers() {
        List<UserDto> dtoUsers = userService.getAllUsers();
        List<EntityModel<UserDto>> userModels = dtoUsers.stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("deleteUser"),
                        linkTo(methodOn(UserController.class).createUser(user)).withRel("createUser"),
                        linkTo(methodOn(UserController.class).updateUser(user.getId(), user)).withRel("updateUser")
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<UserDto>> users = CollectionModel.of(userModels);
        users.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
        return users;
    }
}