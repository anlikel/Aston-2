package com.example.userservicetests;

import com.example.dto.UserDto;
import com.example.entities.UserEntity;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void GetUserById_WhenUserIdExists_ReturnUserDto() {

        Long userId = 1L;
        UserEntity expectedUser = new UserEntity();
        expectedUser.setId(userId);
        expectedUser.setName("Aaaa");
        expectedUser.setEmail("aa@mail.com");
        expectedUser.setAge(30);

        when(userRepository.getUserById(userId)).thenReturn(Optional.of(expectedUser));

        UserDto actualUser = userService.getUserById(userId);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getName(), actualUser.getName());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getAge(), actualUser.getAge());
        assertEquals("OK", actualUser.getResult());
        verify(userRepository).getUserById(userId);
    }

    /**
     * Тест проверяет обработку случая, когда пользователь не найден.
     * Ожидается, что метод вернет UserDto с сообщением об ошибке.
     * Проверяет, что происходит обращение к репозиторию и возвращается корректный DTO с ошибкой.
     */
    @Test
    void GetUserById_WhenUserIdNotExists_ReturnUserDtoWithError() {

        Long nonExistentUserId = 999L;

        when(userRepository.getUserById(nonExistentUserId)).thenReturn(Optional.empty());

        UserDto result = userService.getUserById(nonExistentUserId);

        assertNotNull(result);
        assertEquals("User not found with id: 999", result.getResult());
        assertEquals(null, result.getName());
        assertEquals(null, result.getEmail());
        assertEquals(0, result.getAge());
        verify(userRepository).getUserById(nonExistentUserId);
    }
}