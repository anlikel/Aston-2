package com.example.userservicetests;

import com.example.dto.UserDto;
import com.example.entities.UserEntity;
import com.example.repository.UserRepository;
import com.example.service.KafkaService;
import com.example.service.UserService;
import com.example.util.UtilValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Mock
    KafkaService kafkaService;

    @InjectMocks
    private UserService userService;

    /**
     * Тест успешного создания пользователя с валидными данными
     */
    @Test
    void CreateUser_WhenUserNotExistInDbWithUniqueEmail_ReturnCreatedUser() {

        UserDto newUserDto = new UserDto("Aaaa", "aa@mail.com", 20);

        UserEntity createdUser = new UserEntity();
        createdUser.setId(1L);
        createdUser.setName("Aaaa");
        createdUser.setEmail("aa@mail.com");
        createdUser.setAge(20);
        createdUser.setCreatedAt(LocalDateTime.now());

        when(userRepository.existsByEmail("aa@mail.com")).thenReturn(false);
        when(userRepository.saveUser(any(UserEntity.class))).thenReturn(createdUser);

        UserDto result = userService.createUser(newUserDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Aaaa", result.getName());
        assertEquals("aa@mail.com", result.getEmail());
        assertEquals(20, result.getAge());
        assertEquals("OK", result.getResult());

        verify(userRepository).existsByEmail("aa@mail.com");
        verify(userRepository).saveUser(any(UserEntity.class));
        verify(kafkaService).sendEmailOnUserCreate(createdUser);
    }

    /**
     * Тест создания пользователя с существующим email
     */
    @Test
    void CreateUser_WhenUserExistInDbWithUniqueEmail_ReturnUserDtoWithError() {

        UserDto newUserDto = new UserDto("Aaaa", "aa@mail.com", 20);

        when(userRepository.existsByEmail("aa@mail.com")).thenReturn(true);

        UserDto result = userService.createUser(newUserDto);

        assertNotNull(result);
        assertEquals("User already exists with unique email: aa@mail.com", result.getResult());
        assertEquals("Aaaa", result.getName());
        assertEquals("aa@mail.com", result.getEmail());
        assertEquals(20, result.getAge());

        verify(userRepository).existsByEmail("aa@mail.com");
    }

    /**
     * Тест создания пользователя с некорректным именем
     */
    @Test
    void CreateUser_WhenNewUserNameIncorrect_ReturnUserDtoWithError() {

        UserDto newUserDto = new UserDto("aaaa", "aa@mail.com", 20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidName("aaaa")).thenReturn(false);

            UserDto result = userService.createUser(newUserDto);

            assertEquals("wrong name, should start from Uppercase letter", result.getResult());
            assertEquals("aaaa", result.getName());
            assertEquals("aa@mail.com", result.getEmail());
            assertEquals(20, result.getAge());
            mockedValidator.verify(() -> UtilValidator.isValidName("aaaa"));
        }
    }

    /**
     * Тест создания пользователя с некорректным email
     */
    @Test
    void CreateUser_WhenNewUserEmailIncorrect_ReturnUserDtoWithError() {

        UserDto newUserDto = new UserDto("Aaaa", "aamail.com", 20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidName("Aaaa")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidEmail("aamail.com")).thenReturn(false);

            UserDto result = userService.createUser(newUserDto);

            assertEquals("wrong email", result.getResult());
            assertEquals("Aaaa", result.getName());
            assertEquals("aamail.com", result.getEmail());
            assertEquals(20, result.getAge());
            mockedValidator.verify(() -> UtilValidator.isValidEmail("aamail.com"));
        }
    }

    /**
     * Тест создания пользователя с некорректным возрастом
     */
    @Test
    void CreateUser_WhenNewUserAgeIncorrect_ReturnUserDtoWithError() {

        UserDto newUserDto = new UserDto("Aaaa", "aa@mail.com", 200);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidName("Aaaa")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidEmail("aa@mail.com")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidAge(200)).thenReturn(false);

            UserDto result = userService.createUser(newUserDto);

            assertEquals("wrong age should be in range from 1 to 100", result.getResult());
            assertEquals("Aaaa", result.getName());
            assertEquals("aa@mail.com", result.getEmail());
            assertEquals(200, result.getAge());
            mockedValidator.verify(() -> UtilValidator.isValidAge(200));
        }
    }
}