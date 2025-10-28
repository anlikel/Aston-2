package org.example.dao;

import org.example.entities.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserRepositoryFindAllTest extends HibernateTestAbstract{
    private UserRepository userRepository=new UserRepository();

    @Test
    void shouldFindAllUsers_IfAnyUsersAreInDb() {
        List<UserEntity> users = userRepository.getAllUsers();
        assertEquals(2,users.size());
    }

    @Test
    void shouldFindEmptyList_WhenNoUsersInDB() {
        userRepository.deleteUserById(1L);
        userRepository.deleteUserById(2L);
        List<UserEntity> users = userRepository.getAllUsers();
        assertEquals(0,users.size());
    }
}
