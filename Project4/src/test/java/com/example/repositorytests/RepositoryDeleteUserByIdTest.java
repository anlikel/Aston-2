package com.example.repositorytests;

import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@Sql(scripts = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RepositoryDeleteUserByIdTest extends RepositoryTestAbstract{

    @Autowired
    private UserRepository userRepository;

    @Test
    void DeleteUserById_WhenUserExists_ReturnTrue() {
        boolean result = userRepository.deleteUserById(1L);
        assertTrue(result);
    }

    @Test
    void DeleteUserById_WhenUserNotExists_ReturnFalse() {
        Long wrongId = 4L;
        boolean result = userRepository.deleteUserById(wrongId);
        assertFalse(result);
    }
}