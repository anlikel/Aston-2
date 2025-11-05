package com.example.repositorytests;

import com.example.entities.UserEntity;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Sql(scripts = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RepositoryGetAllUsersTest extends RepositoryTestAbstract{

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldGetAllUsersList_WhenUsersExists_ReturnNonEmptyList() {
        List<UserEntity> users= userRepository.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void shouldGetAllUsersList_WhenUsersNotExists_ReturnEmptyList() {
        userRepository.deleteUserById(1L);
        userRepository.deleteUserById(2L);
        List<UserEntity> users= userRepository.getAllUsers();
        assertEquals(0, users.size());
    }
}
