package com.example.userservicetests;

import com.example.entities.UserEntity;
import com.example.exceptions.MyCustomException;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.util.UtilValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для проверки функциональности создания пользователей в UserService
 */
@ExtendWith(MockitoExtension.class)
class UserServiceCreateUserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Тест успешного создания пользователя с валидными данными
     */
    @Test
    void CreateUser_WhenUserNotExistInDbWithUniqueEmail_ReturnCreatedUser() {
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

    /**
     * Тест создания пользователя с существующим email
     */
    @Test
    void CreateUser_WhenUserExistInDbWithUniqueEmail_ThrowException() {
        UserEntity newUser = new UserEntity();
        newUser.setName("Aaaa");
        newUser.setEmail("aa@mail.com");
        newUser.setAge(20);

        when(userRepository.existsByEmail("aa@mail.com")).thenReturn(true);

        assertThrows(MyCustomException.class, () -> userService.createUser(newUser));
        verify(userRepository).existsByEmail("aa@mail.com");
    }

    /**
     * Тест создания пользователя с некорректным именем
     */
    @Test
    void CreateUser_WhenNewUserNameIncorrect_ThrowException() {
        UserEntity newUser = new UserEntity();
        newUser.setName("aaaa");
        newUser.setEmail("aa@mail.com");
        newUser.setAge(20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidName("aaaa")).thenReturn(false);

            assertThrows(MyCustomException.class, () -> userService.createUser(newUser));
            mockedValidator.verify(() -> UtilValidator.isValidName("aaaa"));
        }
    }

    /**
     * Тест создания пользователя с некорректным email
     */
    @Test
    void CreateUser_WhenNewUserEmailIncorrect_ThrowException() {
        UserEntity newUser = new UserEntity();
        newUser.setName("Aaaa");
        newUser.setEmail("aamail.com");
        newUser.setAge(20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidName("Aaaa")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidEmail("aamail.com")).thenReturn(false);

            assertThrows(MyCustomException.class, () -> userService.createUser(newUser));
            mockedValidator.verify(() -> UtilValidator.isValidEmail("aamail.com"));
        }
    }

    /**
     * Тест создания пользователя с некорректным возрастом
     */
    @Test
    void CreateUser_WhenNewUserAgeIncorrect_ThrowException() {
        UserEntity newUser = new UserEntity();
        newUser.setName("Aaaa");
        newUser.setEmail("aa@mail.com");
        newUser.setAge(200);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidName("Aaaa")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidEmail("aa@mail.com")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidAge(200)).thenReturn(false);

            assertThrows(MyCustomException.class, () -> userService.createUser(newUser));
            mockedValidator.verify(() -> UtilValidator.isValidAge(200));
        }
    }
}