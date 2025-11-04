package com.example.service;

import com.example.entities.UserEntity;
import com.example.exceptions.MyCustomException;
import com.example.repository.UserRepository;
import com.example.util.UtilValidator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Transactional(readOnly = true)
    public UserEntity getUserById(Long userId) {
        return userRepository.getUserById(userId)
                .orElseThrow(() -> new MyCustomException("User not found with id: " + userId));
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void deleteUserById(Long userId) {
        if(!UtilValidator.isValidId(String.valueOf(userId))){
            throw new MyCustomException("wrong id, should be in range from 1 to LongMax");
        }
        if (!userRepository.existsById(userId)) {
            throw new MyCustomException("User not found with id: " + userId);
        }
        userRepository.deleteUserById(userId);
    }

    public UserEntity createUser(UserEntity user) {
        if(!UtilValidator.isValidId(String.valueOf(user.getId()))){
            throw new MyCustomException("wrong id, should be in range from 1 to LongMax");
        }
        if (userRepository.existsById(user.getId())) {
            throw new MyCustomException("User already exists with id: " + user.getId());
        }
        if(!UtilValidator.isValidName(user.getName())){
            throw new MyCustomException("wrong name, should start from Uppercase letter");
        }
        if(!UtilValidator.isValidEmail(user.getEmail())){
            throw new MyCustomException("wrong email");
        }
        return userRepository.save(user);
    }

    public UserEntity updateUser(UserEntity user) {
        if(!UtilValidator.isValidId(String.valueOf(user.getId()))){
            throw new MyCustomException("wrong id, should be in range from 1 to LongMax");
        }
        if (!userRepository.existsById(user.getId())) {
            throw new MyCustomException("User not found with id: " + user.getId());
        }
        if(!UtilValidator.isValidName(user.getName())){
            throw new MyCustomException("wrong name, should start from Uppercase letter");
        }
        if(!UtilValidator.isValidEmail(user.getEmail())){
            throw new MyCustomException("wrong email");
        }
        return userRepository.save(user);
    }
}
