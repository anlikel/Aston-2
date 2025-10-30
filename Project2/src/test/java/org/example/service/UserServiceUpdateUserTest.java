package org.example.service;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки функциональности обновления пользователей в UserService.
 * Проверяет сценарии успешного обновления и случаи, когда пользователь не найден для обновления.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceUpdateTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Тест проверяет успешное обновление пользователя с валидными данными.
     * Ожидается, что метод проверит существование пользователя и выполнит его обновление.
     * Проверяет, что методы репозитория вызываются с правильными параметрами.
     */
    @Test
    void shouldUpdateUser_WhenValidUser() {
        Long goodId = 999L;
        UserEntity user = new UserEntity();
        user.setId(goodId);
        user.setName("User1");
        user.setEmail("Aaaa@mail.com");
        user.setAge(35);

        UserEntity existingUser = new UserEntity();
        when(userRepository.getUserById(goodId)).thenReturn(existingUser);

        userService.updateUser(user);

        verify(userRepository).getUserById(goodId);
        verify(userRepository).updateUser(user);
    }

    /**
     * Тест проверяет выброс исключения при попытке обновления несуществующего пользователя.
     * Ожидается, что метод выбросит MyCustomException, когда пользователь с указанным ID не найден.
     * Проверяет, что обновление не выполняется и происходит только проверка существования пользователя.
     */
    @Test
    void shouldThrowException_WhenUserNotFoundForUpdate() {
        Long badId = 999L;
        UserEntity user = new UserEntity();
        user.setId(badId);
        user.setName("User1");
        user.setEmail("Aaaa@mail.com");
        user.setAge(35);

        when(userRepository.getUserById(badId)).thenReturn(null);

        assertThrows(MyCustomException.class, () -> {
            userService.updateUser(user);
        });

        verify(userRepository).getUserById(badId);
        verify(userRepository, never()).updateUser(user);
    }
}