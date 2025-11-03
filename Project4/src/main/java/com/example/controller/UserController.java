package com.example.controller;

import com.example.dto.CreateUserDto;
import com.example.dto.UpdateUserDto;
import com.example.dto.GetUserDto;
import com.example.entities.UserEntity;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<GetUserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserEntity user = UserMapper.toEntity(createUserDto);
        UserEntity savedUser = userService.save(user);
        return ResponseEntity.ok(UserMapper.toResponseDto(savedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> getUser(@PathVariable Long id) {
        UserEntity user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toResponseDto(user));
    }

    @GetMapping
    public List<GetUserDto> getAllUsers() {
        return userService.findAll().stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserDto updateUserDto) {
        UserEntity user = userService.findById(id);
        UserMapper.updateEntityFromDto(updateUserDto, user);
        UserEntity updatedUser = userService.save(user);
        return ResponseEntity.ok(UserMapper.toResponseDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
