package org.example.dao;

import org.example.entities.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
public class UserRepositorySaveTest extends HibernateTestAbstract {
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() {
        userRepository = new UserRepository();
        UserEntity user = new UserEntity();
        user.setName("Aaaa");
        user.setEmail("aaa@email.com");
        user.setAge(10);
        user.setCreatedAt(LocalDateTime.now());

        Long savedId = userRepository.saveUser(user);

        assertNotNull(savedId);
    }
}
