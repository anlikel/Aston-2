package org.example.service;

import org.example.entities.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тестовый класс для проверки функциональности получения всех пользователей в UserService.
 * Проверяет различные сценарии получения списка пользователей.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceGetAllTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Тест проверяет получение списка всех пользователей, когда в системе есть несколько пользователей.
     * Ожидается, что метод вернет корректный список пользователей с правильными данными.
     * Проверяет размер списка и корректность данных каждого пользователя.
     */
    @Test
    void shouldGetAllUsers_WhenUsersExist() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setName("User1");
        user1.setEmail("aaa@mail.ru");
        user1.setAge(10);

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setName("User2");
        user2.setEmail("bbb@mail.ru");
        user2.setAge(20);

        List<UserEntity> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.getAllUsers()).thenReturn(expectedUsers);

        List<UserEntity> actualUsers = userService.getAllUsers();

        assertEquals(2, actualUsers.size());
        assertEquals("User1", actualUsers.get(0).getName());
        assertEquals("User2", actualUsers.get(1).getName());
        verify(userRepository).getAllUsers();
    }

    /**
     * Тест проверяет получение пустого списка, когда в системе нет пользователей.
     * Ожидается, что метод вернет пустую коллекцию.
     * Проверяет, что список действительно пуст.
     */
    @Test
    void shouldGetEmptyList_WhenNoUsers() {
        when(userRepository.getAllUsers()).thenReturn(Collections.emptyList());

        List<UserEntity> actualUsers = userService.getAllUsers();

        assertTrue(actualUsers.isEmpty());
        verify(userRepository).getAllUsers();
    }

    /**
     * Тест проверяет получение списка пользователей, когда в системе только один пользователь.
     * Ожидается, что метод вернет список с одним пользователем и корректными данными.
     * Проверяет размер списка и корректность данных единственного пользователя.
     */
    @Test
    void shouldGetAllUsers_WhenSingleUser() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setName("User1");
        user1.setEmail("aaa@mail.ru");
        user1.setAge(10);

        List<UserEntity> expectedUsers = Collections.singletonList(user1);
        when(userRepository.getAllUsers()).thenReturn(expectedUsers);

        List<UserEntity> actualUsers = userService.getAllUsers();

        assertEquals(1, actualUsers.size());
        assertEquals("User1", actualUsers.get(0).getName());
        verify(userRepository).getAllUsers();
    }
}