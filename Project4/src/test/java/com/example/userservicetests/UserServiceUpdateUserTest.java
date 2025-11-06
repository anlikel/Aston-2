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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        UserEntity userToUpdate = new UserEntity();
        userToUpdate.setId(1L);
        userToUpdate.setName("Bbbb");
        userToUpdate.setEmail("bb@mail.com");
        userToUpdate.setAge(25);


        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setName("Bbbb");
        savedUser.setEmail("bb@mail.com");
        savedUser.setAge(25);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId("1")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidName("Bbbb")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidEmail("bb@mail.com")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidAge(25)).thenReturn(true);

            when(userRepository.existsById(1L)).thenReturn(true);
            when(userRepository.saveUser(userToUpdate)).thenReturn(savedUser);

            UserEntity result = userService.updateUser(userToUpdate);

            assertNotNull(result);
            assertEquals(1L, result.getId());
            assertEquals("Bbbb", result.getName());
            assertEquals("bb@mail.com", result.getEmail());
            assertEquals(25, result.getAge());

            verify(userRepository).existsById(1L);
            verify(userRepository).saveUser(userToUpdate);

            mockedValidator.verify(() -> UtilValidator.isValidId("1"));
            mockedValidator.verify(() -> UtilValidator.isValidName("Bbbb"));
            mockedValidator.verify(() -> UtilValidator.isValidEmail("bb@mail.com"));
            mockedValidator.verify(() -> UtilValidator.isValidAge(25));
        }
    }

    /**
     * Тест проверяет исключение при невалидном ID
     */
    @Test
    void UpdateUser_WhenUserIdInvalid_ThrowException() {
        UserEntity user = new UserEntity();
        user.setId(-1L);
        user.setName("Aaaa");
        user.setEmail("aa@mail.com");
        user.setAge(20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId("-1")).thenReturn(false);

            assertThrows(MyCustomException.class, () -> {
                userService.updateUser(user);
            });

            mockedValidator.verify(() -> UtilValidator.isValidId("-1"));

            verify(userRepository, never()).existsById(any());
            verify(userRepository, never()).saveUser(any());
        }
    }

    /**
     * Тест проверяет исключение когда пользователь не найден
     */
    @Test
    void UpdateUser_WhenUserNotFound_ThrowException() {
        UserEntity user = new UserEntity();
        user.setId(999L);
        user.setName("Aaaa");
        user.setEmail("aa@mail.com");
        user.setAge(20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId("999")).thenReturn(true);
            when(userRepository.existsById(999L)).thenReturn(false);

            assertThrows(MyCustomException.class, () -> {
                userService.updateUser(user);
            });

            verify(userRepository).existsById(999L);
            verify(userRepository, never()).saveUser(any());
        }
    }

    /**
     * Тест проверяет исключение при невалидном имени
     */
    @Test
    void UpdateUser_WhenUserNameInvalid_ThrowException() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("aaaa");
        user.setEmail("aa@mail.com");
        user.setAge(20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId("1")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidName("aaaa")).thenReturn(false);

            when(userRepository.existsById(1L)).thenReturn(true);

            assertThrows(MyCustomException.class, () -> {
                userService.updateUser(user);
            });

            mockedValidator.verify(() -> UtilValidator.isValidName("aaaa"));
            verify(userRepository, never()).saveUser(any());
        }
    }

    /**
     * Тест проверяет исключение при невалидном email
     */
    @Test
    void UpdateUser_WhenUserEmailInvalid_ThrowException() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Aaaa");
        user.setEmail("invalid-email");
        user.setAge(20);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId("1")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidName("Aaaa")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidEmail("invalid-email")).thenReturn(false);

            when(userRepository.existsById(1L)).thenReturn(true);

            assertThrows(MyCustomException.class, () -> {
                userService.updateUser(user);
            });

            mockedValidator.verify(() -> UtilValidator.isValidEmail("invalid-email"));
            verify(userRepository, never()).saveUser(any());
        }
    }

    /**
     * Тест проверяет исключение при невалидном возрасте
     */
    @Test
    void UpdateUser_WhenUserAgeInvalid_ThrowException() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Aaaa");
        user.setEmail("aa@mail.com");
        user.setAge(150);

        try (MockedStatic<UtilValidator> mockedValidator = Mockito.mockStatic(UtilValidator.class)) {
            mockedValidator.when(() -> UtilValidator.isValidId("1")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidName("Aaaa")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidEmail("aa@mail.com")).thenReturn(true);
            mockedValidator.when(() -> UtilValidator.isValidAge(150)).thenReturn(false);

            when(userRepository.existsById(1L)).thenReturn(true);

            assertThrows(MyCustomException.class, () -> {
                userService.updateUser(user);
            });

            mockedValidator.verify(() -> UtilValidator.isValidAge(150));
            verify(userRepository, never()).saveUser(any());
        }
    }
}
