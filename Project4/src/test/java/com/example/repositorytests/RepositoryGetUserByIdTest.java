package com.example.repositorytests;

import com.example.entities.UserEntity;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@Sql(scripts = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RepositoryGetUserByIdTest extends RepositoryTestAbstract{

    @Autowired
    private UserRepository userRepository;

    @Test
    void GetUserById_WhenIdExists_ReturnUserOptional() {

        UserEntity user= userRepository.getUserById(1L).get();

        assertEquals("User1", user.getName());
        assertEquals("mail1@example.com", user.getEmail());
        assertEquals(25, user.getAge());
    }

    @Test
    void GetUserById_WhenIdNotExists_ReturnEmptyOptional() {

        Optional<UserEntity> userOptional = userRepository.getUserById(9L);

        assertTrue(userOptional.isEmpty());
    }
}
