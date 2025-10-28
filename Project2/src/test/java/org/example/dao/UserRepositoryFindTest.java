package org.example.dao;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserRepositoryFindTest extends HibernateTestAbstract{
    private UserRepository userRepository=new UserRepository();

    @Test
    void shouldFindUserById_WhenUserExists() {
        UserEntity foundUser = userRepository.getUserById(1L);

        assertEquals("User1",foundUser.getName());
        assertEquals("mail1@example.com",foundUser.getEmail());
        assertEquals(25,foundUser.getAge());
    }

    @Test
    void shouldThrowException_WhenUserNotFound() {

        Long wrongId = 4L;

        MyCustomException exception = assertThrows(MyCustomException.class, () -> {
            userRepository.getUserById(wrongId);
        });
    }
}