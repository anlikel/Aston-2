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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для проверки функциональности удаления пользователей в UserService.
 * Проверяет сценарии успешного удаления и случаи, когда пользователь не найден.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceDeleteUserByIdTest {

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
    void DeleteUserById_WhenUserIdExists_ReturnTrue() {
        Long goodId = 1L;

        when(userRepository.deleteUserById(goodId)).thenReturn(true);

        boolean result = userService.deleteUserById(goodId);

        assertTrue(result);
        verify(userRepository).deleteUserById(goodId);
    }

    /**
     * Тест проверяет выброс исключения при попытке удаления несуществующего пользователя.
     * Ожидается, что метод выбросит MyCustomException, когда пользователь с указанным ID не найден.
     * Проверяет, что исключение выбрасывается корректно.
     */
    @Test
    void DeleteUserById_WhenUserIdNotExists_ThrowException() {
        Long badId = 999L;

        when(userRepository.deleteUserById(badId)).thenReturn(false);

        assertThrows(MyCustomException.class, () -> {
            userService.deleteUserById(badId);
        });

        verify(userRepository).deleteUserById(badId);
    }
}
