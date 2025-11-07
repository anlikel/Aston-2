package com.example.repositorytests;

import com.example.entities.UserEntity;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Sql(scripts = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RepositorySaveUserTest extends RepositoryTestAbstract {

    @Autowired
    private UserRepository userRepository;

    /**
     * Тест обновления существующего пользователя
     */
    @Test
    void SaveUser_WhenIdExists_ReturnUpdatedUserWithOldId() {
        UserEntity newUser = new UserEntity();
        newUser.setId(1L);
        newUser.setName("Aaaa");
        newUser.setEmail("aaa@mail.ru");
        newUser.setAge(55);

        UserEntity user = userRepository.saveUser(newUser);

        assertEquals(1L, user.getId());
        assertEquals("Aaaa", user.getName());
        assertEquals("aaa@mail.ru", user.getEmail());
        assertEquals(55, user.getAge());
    }

    /**
     * Тест создания нового пользователя
     */
    @Test
    void SaveUser_WhenIdNotExists_ReturnCreatedUserWithNewId() {
        UserEntity newUser = new UserEntity();
        newUser.setName("Aaaa");
        newUser.setEmail("aaa@mail.ru");
        newUser.setAge(55);

        UserEntity user = userRepository.saveUser(newUser);

        assertEquals(3L, user.getId());
        assertEquals("Aaaa", user.getName());
        assertEquals("aaa@mail.ru", user.getEmail());
        assertEquals(55, user.getAge());
    }
}