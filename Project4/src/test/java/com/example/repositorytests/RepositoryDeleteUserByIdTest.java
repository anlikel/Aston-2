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
public class RepositoryDeleteUserByIdTest extends RepositoryTestAbstract {

    @Autowired
    private UserRepository userRepository;

    /**
     * Тест успешного удаления пользователя
     */
    @Test
    void DeleteUserById_WhenUserExists_ReturnTrue() {
        boolean result = userRepository.deleteUserById(1L);
        assertTrue(result);
    }

    /**
     * Тест удаления несуществующего пользователя
     */
    @Test
    void DeleteUserById_WhenUserNotExists_ReturnFalse() {
        boolean result = userRepository.deleteUserById(4L);
        assertFalse(result);
    }
}