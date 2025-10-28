package org.example.dao;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserRepositoryDeleteTest extends HibernateTestAbstract{
    private UserRepository userRepository=new UserRepository();

    @Test
    void shouldDeleteUserById_WhenUserExists() {
        UserEntity deletedUser = userRepository.deleteUserById(1L);

        assertEquals("User1",deletedUser.getName());
        assertEquals("mail1@example.com",deletedUser.getEmail());
        assertEquals(25,deletedUser.getAge());
    }

    @Test
    void shouldThrowException_WhenUserNotExists() {

        Long wrongId = 4L;

        assertThrows(MyCustomException.class, () -> {
            userRepository.getUserById(wrongId);
        });
    }
}
