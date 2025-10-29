package org.example.service;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUpdateTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldUpdateUser_WhenValidUser() {
        Long goodId = 999L;
        UserEntity user = new UserEntity();
        user.setId(goodId);
        user.setName("User1");
        user.setEmail("Aaaa@mail.com");
        user.setAge(35);

        UserEntity existingUser = new UserEntity();
        when(userRepository.getUserById(goodId)).thenReturn(existingUser);

        userService.updateUser(user);

        verify(userRepository).getUserById(goodId);
        verify(userRepository).updateUser(user);
    }

    @Test
    void shouldThrowException_WhenUserNotFoundForUpdate() {

        Long badId = 999L;
        UserEntity user = new UserEntity();
        user.setId(badId);
        user.setName("User1");
        user.setEmail("Aaaa@mail.com");
        user.setAge(35);

        when(userRepository.getUserById(badId)).thenReturn(null);

        assertThrows(MyCustomException.class, () -> {
            userService.updateUser(user);
        });

        verify(userRepository).getUserById(badId);

        verify(userRepository, never()).updateUser(user);
    }


}
