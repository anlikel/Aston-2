package com.example.controller;

import com.example.dto.CreateUserDto;
import com.example.dto.GetUserDto;
import com.example.entities.UserEntity;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<GetUserDto> createUser(@RequestBody CreateUserDto createUserDto) {
        UserEntity user = UserMapper.toEntity(createUserDto);
        UserEntity savedUser = userService.createUser(user);
        return ResponseEntity.ok(UserMapper.toGetUserDto(savedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> getUser(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toGetUserDto(user));
    }

    @GetMapping
    public List<GetUserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserMapper::toGetUserDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserDto> updateUser(
            @PathVariable Long id,
            @RequestBody CreateUserDto updateUserDto) {
        UserEntity user = UserMapper.toEntity(updateUserDto);
        user.setId(id);
        UserEntity updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(UserMapper.toGetUserDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
