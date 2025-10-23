package org.example.repository;

import org.example.entities.User;

import java.util.List;

public interface UserDao {
    public Long saveUser(User user);
    public User getUserById(Long id);
    public List<User> getAllUsers();
    public void updateUserById(User user);
    public void deleteUserById(Long id);
}
