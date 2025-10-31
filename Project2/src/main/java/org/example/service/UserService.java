package org.example.service;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;

import java.util.List;

/**
 * Сервис для управления пользователями.
 * Предоставляет бизнес-логику для операций с пользователями.
 */
public class UserService {

    private final UserRepository userRepository;

    /**
     * Конструктор с внедрением зависимости репозитория.
     *
     * @param userRepository репозиторий для работы с данными пользователей
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Создает нового пользователя.
     *
     * @param user сущность пользователя для создания
     * @return идентификатор созданного пользователя
     */
    public Long createUser(UserEntity user) {
        return userRepository.saveUser(user);
    }

    /**
     * Получает пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return сущность пользователя
     * @throws MyCustomException если пользователь с указанным id не найден
     */
    public UserEntity getUserById(Long id) {
        UserEntity user = userRepository.getUserById(id);
        if (user == null) {
            throw new MyCustomException("User not found with id: " + id);
        }
        return user;
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список сущностей пользователей
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.getAllUsers();
    }

    /**
     * Обновляет данные пользователя.
     *
     * @param user сущность пользователя с обновленными данными
     * @throws MyCustomException если пользователь с указанным id не найден
     */
    public void updateUser(UserEntity user) {
        UserEntity oldUser = userRepository.getUserById(user.getId());
        if (oldUser == null) {
            throw new MyCustomException("Cannot update user: user with id " + user.getId() + " not found");
        }
        userRepository.updateUser(user);
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя для удаления
     * @return сущность удаленного пользователя
     * @throws MyCustomException если пользователь с указанным id не найден
     */
    public UserEntity deleteUser(Long id) {
        UserEntity user = userRepository.deleteUserById(id);
        if (user == null) {
            throw new MyCustomException("User not found with id: " + id);
        }
        return user;
    }
}