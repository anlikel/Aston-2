package org.example.service;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для проверки функциональности получения пользователя по идентификатору в UserService.
 * Проверяет сценарии успешного получения пользователя и случаи, когда пользователь не найден.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceGetUserByIdTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Тест проверяет успешное получение пользователя по существующему идентификатору.
     * Ожидается, что метод вернет пользователя с корректными данными.
     * Проверяет, что все поля возвращенного пользователя соответствуют ожидаемым значениям.
     */
    @Test
    void shouldGetUserById_WhenUserExists() {
        Long userId = 1L;
        UserEntity expectedUser = new UserEntity();
        expectedUser.setId(userId);
        expectedUser.setName("Aaaa");
        expectedUser.setEmail("aa@mail.com");
        expectedUser.setAge(30);

        when(userRepository.getUserById(userId)).thenReturn(expectedUser);

        UserEntity actualUser = userService.getUserById(userId);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getName(), actualUser.getName());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getAge(), actualUser.getAge());
        verify(userRepository).getUserById(userId);
    }

    /**
     * Тест проверяет выброс исключения при попытке получения несуществующего пользователя.
     * Ожидается, что метод выбросит MyCustomException, когда пользователь с указанным ID не найден.
     * Проверяет, что исключение выбрасывается корректно и происходит обращение к репозиторию.
     */
    @Test
    void shouldThrowException_WhenUserNotFound() {
        Long nonExistentUserId = 999L;

        when(userRepository.getUserById(nonExistentUserId)).thenReturn(null);

        assertThrows(MyCustomException.class, () -> {
            userService.getUserById(nonExistentUserId);
        });

        verify(userRepository).getUserById(nonExistentUserId);
    }
}