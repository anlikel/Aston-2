package com.example.repository;

import com.example.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    /**
     * Создать нового пользователя
     *
     * @param user сущность пользователя
     * @return сохраненный пользователь
     */
    default UserEntity saveUser(UserEntity user) {
        return save(user);
    }

    /**
     * Получить пользователя по ID
     *
     * @param id идентификатор пользователя
     * @return Optional с пользователем
     */
    default Optional<UserEntity> getUserById(Long id) {
        return findById(id);
    }

    /**
     * Получить всех пользователей
     *
     * @return список всех пользователей
     */
    default List<UserEntity> getAllUsers() {
        return findAll();
    }

    /**
     * Удалить пользователя по ID
     *
     * @param id идентификатор пользователя
     * @return Optional<UserEntity> если пользователь удален, empty если не найден
     */
    default Optional<UserEntity> deleteUserById(Long id) {
        Optional<UserEntity> userOptional = findById(id);
        if (userOptional.isPresent()) {
            deleteById(id);
        }
        return userOptional;
    }
}
