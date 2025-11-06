package com.example.service;

import com.example.entities.UserEntity;
import com.example.exceptions.MyCustomException;
import com.example.repository.UserRepository;
import com.example.util.UtilValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserEntity getUserById(Long userId) {
        logger.info("DB event: try to getUserById {}", userId);
        try {
            UserEntity user = userRepository.getUserById(userId)
                    .orElseThrow(() -> new MyCustomException("User not found with id: " + userId));
            logger.info("DB event: success getUserById {}", userId);
            return user;
        } catch (MyCustomException e) {
            logger.error("DB event: failed getUserById {}, error: {}", userId, e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getAllUsers() {
        logger.info("DB event: try to getAllUsers");
        List<UserEntity> users = userRepository.getAllUsers();
        logger.info("DB event: success getAllUsers, found {} users", users.size());
        return users;
    }

    public boolean deleteUserById(Long userId) {
        logger.info("DB event: try to deleteUserById {}", userId);

        if (!UtilValidator.isValidId(String.valueOf(userId))) {
            logger.warn("DB event: validation failed for deleteUserById {}, wrong id format", userId);
            throw new MyCustomException("wrong id, should be in range from 1 to LongMax");
        }
        boolean result = userRepository.deleteUserById(userId);
        if (!result) {
            logger.error("DB event: user not found for deleteUserById {}", userId);
            throw new MyCustomException("User not found with id: " + userId);
        }
        logger.info("DB event: success deleteUserById {}", userId);
        return result;
    }

    public UserEntity createUser(UserEntity user) {
        logger.info("DB event: try to createUser {}", user.getName());
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.error("DB event: user already exists with unique email {}", user.getEmail());
            throw new MyCustomException("User already exists with unique email: " + user.getEmail());
        }
        if (!UtilValidator.isValidName(user.getName())) {
            logger.warn("DB event: validation failed for createUser {}, wrong name format", user.getId());
            throw new MyCustomException("wrong name, should start from Uppercase letter");
        }
        if (!UtilValidator.isValidEmail(user.getEmail())) {
            logger.warn("DB event: validation failed for createUser {}, wrong email format", user.getEmail());
            throw new MyCustomException("wrong email");
        }
        if (!UtilValidator.isValidAge(user.getAge())) {
            logger.warn("DB event: validation failed for createUser {}, wrong age format", user.getAge());
            throw new MyCustomException("wrong age should be in range from 1 to 100");
        }
        UserEntity savedUser = userRepository.saveUser(user);
        logger.info("DB event: success createUser {} with id {}", user.getName(), savedUser.getId());
        return savedUser;
    }

    public UserEntity updateUser(UserEntity user) {
        logger.info("DB event: try to updateUser {}", user.getId());
        if (!UtilValidator.isValidId(String.valueOf(user.getId()))) {
            logger.warn("DB event: validation failed for updateUser {}, wrong id ", user.getId());
            throw new MyCustomException("wrong id, should be in range from 1 to LongMax");
        }
        if (!userRepository.existsById(user.getId())) {
            logger.error("DB event: user not found for updateUser {}", user.getId());
            throw new MyCustomException("User not found with id: " + user.getId());
        }
        if (!UtilValidator.isValidName(user.getName())) {
            logger.warn("DB event: validation failed for updateUser {}, wrong name format", user.getId());
            throw new MyCustomException("wrong name, should start from Uppercase letter");
        }
        if (!UtilValidator.isValidEmail(user.getEmail())) {
            logger.warn("DB event: validation failed for updateUser {}, wrong email format", user.getEmail());
            throw new MyCustomException("wrong email");
        }
        if (!UtilValidator.isValidAge(user.getAge())) {
            logger.warn("DB event: validation failed for updateUser {}, wrong age format", user.getAge());
            throw new MyCustomException("wrong age should be in range from 1 to 100");
        }
        UserEntity updatedUser = userRepository.saveUser(user);
        logger.info("DB event: success updateUser {}", user.getId());
        return updatedUser;
    }
}
