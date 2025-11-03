package org.example.service;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки функциональности удаления пользователей в UserService.
 * Проверяет сценарии успешного удаления и случаи, когда пользователь не найден.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceDeleteUserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Тест проверяет успешное удаление существующего пользователя.
     * Ожидается, что метод вернет удаленного пользователя с корректными данными.
     * Проверяет, что все поля пользователя соответствуют ожидаемым значениям.
     */
    @Test
    void shouldDeleteUser_WhenUserExists() {
        Long goodId = 1L;
        UserEntity expectedUser = new UserEntity();
        expectedUser.setId(goodId);
        expectedUser.setName("Aaa");
        expectedUser.setEmail("aaa@mail.com");

        when(userRepository.deleteUserById(goodId)).thenReturn(expectedUser);

        UserEntity deletedUser = userService.deleteUser(goodId);

        assertNotNull(deletedUser);
        assertEquals(goodId, deletedUser.getId());
        assertEquals("Aaa", deletedUser.getName());
        verify(userRepository).deleteUserById(goodId);
    }

    /**
     * Тест проверяет выброс исключения при попытке удаления несуществующего пользователя.
     * Ожидается, что метод выбросит MyCustomException, когда пользователь с указанным ID не найден.
     * Проверяет, что исключение выбрасывается корректно.
     */
    @Test
    void shouldThrowException_WhenUserNotFound() {
        Long badId = 999L;

        when(userRepository.deleteUserById(badId)).thenReturn(null);

        assertThrows(MyCustomException.class, () -> {
            userService.deleteUser(badId);
        });

        verify(userRepository).deleteUserById(badId);
    }
}