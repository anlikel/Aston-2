package org.example.service;

import org.example.entities.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для проверки функциональности создания пользователей в UserService.
 * Проверяет различные сценарии создания пользователей с разными наборами данных.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceCreateTest {

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
    void shouldCreateUser_WhenValidUser() {
        UserEntity user = new UserEntity();
        user.setName("Aaaa");
        user.setEmail("aa@mail.com");
        user.setAge(20);

        Long expectedUserId = 1L;
        when(userRepository.saveUser(user)).thenReturn(expectedUserId);

        Long actualUserId = userService.createUser(user);

        assertEquals(expectedUserId, actualUserId);
        verify(userRepository).saveUser(user);
    }

    /**
     * Тест проверяет создание пользователя с минимальным набором данных.
     * Пользователь имеет только имя, остальные поля не заполнены.
     * Ожидается, что метод вернет корректный идентификатор пользователя.
     */
    @Test
    void shouldCreateUser_WhenUserWithMinimalData() {
        UserEntity user = new UserEntity();
        user.setName("Bbb");

        when(userRepository.saveUser(user)).thenReturn(2L);

        Long userId = userService.createUser(user);

        assertEquals(2L, userId);
        verify(userRepository).saveUser(user);
    }
}