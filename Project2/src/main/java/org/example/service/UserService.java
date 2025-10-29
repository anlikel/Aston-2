package org.example.service;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(UserEntity user) {
        return userRepository.saveUser(user);
    }

    public UserEntity getUserById(Long id) {
        UserEntity user = userRepository.getUserById(id);
        if (user == null) {
            throw new MyCustomException("User not found with id: " + id);
        }
        return user;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void updateUser(UserEntity user) {
        UserEntity oldUser = userRepository.getUserById(user.getId());
        if (oldUser == null) {
            throw new MyCustomException("Cannot update user: user with id " + user.getId() + " not found");
        }
        userRepository.updateUser(user);
    }

    public UserEntity deleteUser(Long id) {
        UserEntity user = userRepository.deleteUserById(id);
        if (user == null) {
            throw new MyCustomException("User not found with id: " + id);
        }
        return user;
    }
}