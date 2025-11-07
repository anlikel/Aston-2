package com.example.dto;

import com.example.entities.UserEntity;

import java.time.LocalDateTime;

/**
 * Маппер для преобразования между Entity и DTO
 */
public class UserMapper {

    /**
     * Преобразование UserEntity в GetUserDto
     */
    public static GetUserDto toGetUserDto(UserEntity user) {
        return new GetUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getCreatedAt()
        );
    }

    /**
     * Преобразование CreateUserDto в UserEntity
     */
    public static UserEntity toEntity(CreateUserDto createUserDto) {
        UserEntity user = new UserEntity();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        user.setAge(createUserDto.getAge());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}
