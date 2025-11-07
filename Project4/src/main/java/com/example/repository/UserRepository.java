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
     * @return true если пользователь удален, false если не найден
     */
    default boolean deleteUserById(Long id) {
        if (existsById(id)) {
            deleteById(id);
            return true;
        }
        return false;
    }
}
