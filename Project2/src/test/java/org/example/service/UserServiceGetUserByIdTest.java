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

@ExtendWith(MockitoExtension.class)
class UserServiceGetByIdTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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
