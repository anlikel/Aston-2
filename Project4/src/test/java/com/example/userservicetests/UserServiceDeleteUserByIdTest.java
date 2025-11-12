package com.example.userservicetests;

import com.example.dto.UserDto;
import com.example.entities.UserEntity;
import com.example.repository.UserRepository;
import com.example.service.KafkaService;
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
 * Тестовый класс для проверки функциональности удаления пользователей в UserService.
 * Проверяет сценарии успешного удаления и случаи, когда пользователь не найден.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceDeleteUserByIdTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    KafkaService kafkaService;

    @InjectMocks
    private UserService userService;

    /**
     * Тест проверяет успешное удаление существующего пользователя.
     * Ожидается, что метод вернет удаленного пользователя с корректными данными.
     * Проверяет, что все поля пользователя соответствуют ожидаемым значениям.
     */
    @Test
    void DeleteUserById_WhenUserIdExists_ReturnDeletedUserDto() {

        Long goodId = 1L;
        UserEntity expectedUser = new UserEntity();
        expectedUser.setId(1L);
        expectedUser.setName("Aaaa");
        expectedUser.setEmail("aaa@mail.ru");
        expectedUser.setAge(12);

        when(userRepository.deleteUserById(goodId)).thenReturn(Optional.of(expectedUser));


        UserDto result = userService.deleteUserById(goodId);

        assertNotNull(result);
        assertEquals(expectedUser.getId(), result.getId());
        assertEquals(expectedUser.getName(), result.getName());
        assertEquals(expectedUser.getEmail(), result.getEmail());
        assertEquals(expectedUser.getAge(), result.getAge());
        assertEquals("OK", result.getResult());

        verify(userRepository).deleteUserById(goodId);
        verify(kafkaService).sendEmailOnUserDelete(expectedUser);
    }

    /**
     * Тест проверяет обработку случая, когда пользователь не найден.
     * Ожидается, что метод вернет UserDto с сообщением об ошибке.
     */
    @Test
    void DeleteUserById_WhenUserIdNotExists_ReturnUserDtoWithError() {

        Long badId = 999L;

        when(userRepository.deleteUserById(badId)).thenReturn(Optional.empty());

        UserDto result = userService.deleteUserById(badId);

        assertNotNull(result);
        assertEquals("User not found with id: 999", result.getResult());
        assertEquals(null, result.getName());
        assertEquals(null, result.getEmail());
        assertEquals(0, result.getAge());

        verify(userRepository).deleteUserById(badId);
    }

    /**
     * Тест проверяет обработку невалидного ID пользователя.
     * Ожидается, что метод вернет UserDto с сообщением об ошибке валидации.
     */
    @Test
    void DeleteUserById_WhenUserIdInvalid_ReturnUserDtoWithError() {

        Long invalidId = -1L;

        UserDto result = userService.deleteUserById(invalidId);

        assertNotNull(result);
        assertEquals("wrong id, should be in range from 1 to LongMax", result.getResult());
        assertEquals(null, result.getName());
        assertEquals(null, result.getEmail());
        assertEquals(0, result.getAge());
    }
}