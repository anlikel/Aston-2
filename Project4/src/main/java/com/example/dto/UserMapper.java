package com.example.dto;

import com.example.entities.UserEntity;

/**
 * Маппер для преобразования между Entity и DTO
 */
public class UserMapper {

    /**
     * Преобразование UserEntity в GetUserDto
     */
    public static UserDto toUserDto(UserEntity user) {
        UserDto userDto = new UserDto(
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
        userDto.setId(user.getId());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }

    /**
     * Преобразование UserDto в UserEntity
     */
    public static UserEntity toEntity(UserDto UserDto) {
        UserEntity user = new UserEntity();
        user.setName(UserDto.getName());
        user.setEmail(UserDto.getEmail());
        user.setAge(UserDto.getAge());
        return user;
    }
}
