package com.example.userservicetests;

import com.example.entities.UserEntity;
import com.example.exceptions.MyCustomException;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для проверки функциональности создания пользователей в UserService.
 * Проверяет различные сценарии создания пользователей с разными наборами данных.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceCreateUserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Тест проверяет успешное создание пользователя с валидными данными.
     * Пользователь имеет имя, email и возраст.
     * Ожидается, что метод вернет корректный идентификатор пользователя.
     */
    @Test
    void shouldCreateUser_WhenUserNotExistInDbWithUniqueEmail_ReturnCreatedUser() {
        UserEntity newUser = new UserEntity();
        newUser.setName("Aaaa");
        newUser.setEmail("aa@mail.com");
        newUser.setAge(20);

        UserEntity createdUser = new UserEntity();
        createdUser.setId(1L);
        createdUser.setName("Aaaa");
        createdUser.setEmail("aa@mail.com");
        createdUser.setAge(20);
        createdUser.setCreatedAt(LocalDateTime.now());

        when(userRepository.existsByEmail("aa@mail.com")).thenReturn(false);
        when(userRepository.saveUser(any(UserEntity.class))).thenReturn(createdUser);

        UserEntity result = userService.createUser(newUser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Aaaa", result.getName());
        assertEquals("aa@mail.com", result.getEmail());
        assertEquals(20, result.getAge());
        assertNotNull(result.getCreatedAt());

        verify(userRepository).existsByEmail("aa@mail.com");
        verify(userRepository).saveUser(any(UserEntity.class));
    }

    @Test
    void shouldCreateUser_WhenUserExistInDbWithUniqueEmail_ThrowException() {
        UserEntity newUser = new UserEntity();
        newUser.setName("Aaaa");
        newUser.setEmail("aa@mail.com");
        newUser.setAge(20);

        when(userRepository.existsByEmail("aa@mail.com")).thenReturn(true);

        assertThrows(MyCustomException.class, () -> {
            userService.createUser(newUser);
        });

        verify(userRepository).existsByEmail("aa@mail.com");
    }
}
