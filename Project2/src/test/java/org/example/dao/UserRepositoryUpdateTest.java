package org.example.dao;

import org.example.entities.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserRepositoryUpdateTest extends HibernateTestAbstract {
    private UserRepository userRepository = new UserRepository();

    @Test
    void shouldUpdateUser_WhenUserExists() {

        UserEntity oldUser = userRepository.getUserById(1L);
        UserEntity newUser = new UserEntity();
        newUser.setId(oldUser.getId());
        newUser.setName("Aaaa");
        newUser.setEmail("aaa@email.com");
        newUser.setAge(10);
        newUser.setCreatedAt(LocalDateTime.now());

        userRepository.updateUser(newUser);
        UserEntity updatedUser = userRepository.getUserById(oldUser.getId());

        assertEquals(newUser.getName(), updatedUser.getName());
        assertEquals(newUser.getEmail(), updatedUser.getEmail());
        assertEquals(newUser.getAge(), updatedUser.getAge());
    }

    @Test
    void shouldUpdateUser_WhenUserNotExists() {

        UserEntity newUser = new UserEntity();
        newUser.setId(999L);
        newUser.setName("Aaaa");
        newUser.setEmail("aaa@email.com");
        newUser.setAge(10);
        newUser.setCreatedAt(LocalDateTime.now());

        assertThrows(Exception.class, () -> {
            userRepository.updateUser(newUser);
        });

    }
}
