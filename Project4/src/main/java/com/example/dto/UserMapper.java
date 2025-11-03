package com.example.mapper;

import com.example.dto.GetUserDto;
import com.example.entities.UserEntity;
import com.example.dto.CreateUserDto;
import com.example.dto.UpdateUserDto;

/**
 * Маппер для преобразования между Entity и DTO
 */
public class UserMapper {

    public static GetUserDto toResponseDto(UserEntity user) {
        return new GetUserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getCreatedAt()
        );
    }

    public static UserEntity toEntity(CreateUserDto createUserDto) {
        UserEntity user = new UserEntity();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        user.setAge(createUserDto.getAge());
        return user;
    }

    public static void updateEntityFromDto(UpdateUserDto updateUserDto, UserEntity user) {
        if (updateUserDto.getName() != null) {
            user.setName(updateUserDto.getName());
        }
        if (updateUserDto.getEmail() != null) {
            user.setEmail(updateUserDto.getEmail());
        }
        if (updateUserDto.getAge() != null) {
            user.setAge(updateUserDto.getAge());
        }
    }
}
