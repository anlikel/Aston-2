package com.example.controller;

import com.example.dto.CreateUserDto;
import com.example.dto.GetUserDto;
import com.example.entities.UserEntity;
import com.example.exceptions.MyCustomException;
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
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto) {
        try {
            UserEntity user = UserMapper.toEntity(createUserDto);
            UserEntity savedUser = userService.createUser(user);
            return ResponseEntity.ok(UserMapper.toGetUserDto(savedUser));
        } catch (MyCustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try{
        UserEntity user = userService.getUserById(id);
            return ResponseEntity.ok(UserMapper.toGetUserDto(user));
        }catch (MyCustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<GetUserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserMapper::toGetUserDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody CreateUserDto updateUserDto) {
        UserEntity user = UserMapper.toEntity(updateUserDto);
        user.setId(id);
        try {
            UserEntity updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(UserMapper.toGetUserDto(updatedUser));
        }catch (MyCustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("deleted");
        } catch (MyCustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
