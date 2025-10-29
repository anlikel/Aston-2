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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceDeleteUserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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