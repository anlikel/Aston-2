package com.example.service;

import com.example.controller.UserController;
import com.example.dto.UserDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class HateoasService {

    private final UserService userService;

    public HateoasService(UserService userService) {
        this.userService = userService;
    }

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
