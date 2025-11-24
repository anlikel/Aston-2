package com.example.userservicetests;

import com.example.dto.UserDto;
import com.example.entities.UserEntity;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class UserServiceUpdateUserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Тест проверяет успешное обновление пользователя с валидными данными.
     */
    @Test
    void UpdateUser_WhenUserExistsWithValidData_ReturnUpdatedUser() {

        Long userId = 1L;
        UserDto userToUpdate = new UserDto("Bbbb", "bb@mail.com", 25);

        UserEntity savedUser = new UserEntity();
        savedUser.setId(userId);
        savedUser.setName("Bbbb");
        savedUser.setEmail("bb@mail.com");
        savedUser.setAge(25);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId(String.valueOf(userId))).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidName("Bbbb")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidEmail("bb@mail.com")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidAge(25)).thenReturn(true);

            when(userRepository.existsById(userId)).thenReturn(true);
            when(userRepository.saveUser(any(UserEntity.class))).thenReturn(savedUser);

            UserDto result = userService.updateUser(userToUpdate, userId);

            assertNotNull(result);
            assertEquals(userId, result.getId());
            assertEquals("Bbbb", result.getName());
            assertEquals("bb@mail.com", result.getEmail());
            assertEquals(25, result.getAge());
            assertEquals("OK", result.getResult());

            verify(userRepository).existsById(userId);
            verify(userRepository).saveUser(any(UserEntity.class));

            mockedValidator.verify(() -> UtilValidator.isValidId(String.valueOf(userId)));
            mockedValidator.verify(() -> UtilValidator.isValidName("Bbbb"));
            mockedValidator.verify(() -> UtilValidator.isValidEmail("bb@mail.com"));
            mockedValidator.verify(() -> UtilValidator.isValidAge(25));
        }
    }

    /**
     * Тест проверяет обработку невалидного ID
     */
    @Test
    void UpdateUser_WhenUserIdInvalid_ReturnUserDtoWithError() {

        Long invalidUserId = -1L;
        UserDto userDto = new UserDto("Aaaa", "aa@mail.com", 20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId(String.valueOf(invalidUserId))).thenReturn(false);

            UserDto result = userService.updateUser(userDto, invalidUserId);

            assertNotNull(result);
            assertEquals("wrong id, should be in range from 1 to LongMax", result.getResult());
            assertEquals("Aaaa", result.getName());
            assertEquals("aa@mail.com", result.getEmail());
            assertEquals(20, result.getAge());

            mockedValidator.verify(() -> UtilValidator.isValidId(String.valueOf(invalidUserId)));

            verify(userRepository, never()).existsById(any());
            verify(userRepository, never()).saveUser(any());
        }
    }

    /**
     * Тест проверяет обработку случая когда пользователь не найден
     */
    @Test
    void UpdateUser_WhenUserNotFound_ReturnUserDtoWithError() {
        // Arrange
        Long nonExistentUserId = 999L;
        UserDto userDto = new UserDto("Aaaa", "aa@mail.com", 20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId(String.valueOf(nonExistentUserId))).thenReturn(true);
            when(userRepository.existsById(nonExistentUserId)).thenReturn(false);

            UserDto result = userService.updateUser(userDto, nonExistentUserId);

            assertNotNull(result);
            assertEquals("User not found with id: 999", result.getResult());
            assertEquals("Aaaa", result.getName());
            assertEquals("aa@mail.com", result.getEmail());
            assertEquals(20, result.getAge());

            verify(userRepository).existsById(nonExistentUserId);
            verify(userRepository, never()).saveUser(any());
        }
    }

    /**
     * Тест проверяет обработку невалидного имени
     */
    @Test
    void UpdateUser_WhenUserNameInvalid_ReturnUserDtoWithError() {

        Long userId = 1L;
        UserDto userDto = new UserDto("aaaa", "aa@mail.com", 20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId(String.valueOf(userId))).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidName("aaaa")).thenReturn(false);

            when(userRepository.existsById(userId)).thenReturn(true);

            UserDto result = userService.updateUser(userDto, userId);

            assertNotNull(result);
            assertEquals("wrong name, should start from Uppercase letter", result.getResult());
            assertEquals("aaaa", result.getName());
            assertEquals("aa@mail.com", result.getEmail());
            assertEquals(20, result.getAge());

            mockedValidator.verify(() -> UtilValidator.isValidName("aaaa"));
            verify(userRepository, never()).saveUser(any());
        }
    }

    /**
     * Тест проверяет обработку невалидного email
     */
    @Test
    void UpdateUser_WhenUserEmailInvalid_ReturnUserDtoWithError() {

        Long userId = 1L;
        UserDto userDto = new UserDto("Aaaa", "invalid-email", 20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId(String.valueOf(userId))).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidName("Aaaa")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidEmail("invalid-email")).thenReturn(false);

            when(userRepository.existsById(userId)).thenReturn(true);

            UserDto result = userService.updateUser(userDto, userId);

            assertNotNull(result);
            assertEquals("wrong email", result.getResult());
            assertEquals("Aaaa", result.getName());
            assertEquals("invalid-email", result.getEmail());
            assertEquals(20, result.getAge());

            mockedValidator.verify(() -> UtilValidator.isValidEmail("invalid-email"));
            verify(userRepository, never()).saveUser(any());
        }
    }

    /**
     * Тест проверяет обработку невалидного возраста
     */
    @Test
    void UpdateUser_WhenUserAgeInvalid_ReturnUserDtoWithError() {

        Long userId = 1L;
        UserDto userDto = new UserDto("Aaaa", "aa@mail.com", 150);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId(String.valueOf(userId))).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidName("Aaaa")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidEmail("aa@mail.com")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidAge(150)).thenReturn(false);

            when(userRepository.existsById(userId)).thenReturn(true);

            UserDto result = userService.updateUser(userDto, userId);

            assertNotNull(result);
            assertEquals("wrong age should be in range from 1 to 100", result.getResult());
            assertEquals("Aaaa", result.getName());
            assertEquals("aa@mail.com", result.getEmail());
            assertEquals(150, result.getAge());

            mockedValidator.verify(() -> UtilValidator.isValidAge(150));
            verify(userRepository, never()).saveUser(any());
        }
    }
}