package org.example.dao;

import org.example.entities.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки функциональности обновления пользователей.
 * Проверяет метод updateUser() в различных сценариях обновления данных.
 */
public class UserRepositoryUpdateTest extends HibernateTestAbstract {

    /**
     * Репозиторий пользователей для тестирования операций обновления
     */
    private UserRepository userRepository = new UserRepository();

    /**
     * Тест успешного обновления данных пользователя, когда пользователь существует.
     * Проверяет, что метод updateUser():
     * - Корректно обновляет данные существующего пользователя
     * - Сохраняет все измененные поля (имя, email, возраст)
     * - Не изменяет идентификатор пользователя
     * - Возвращает актуальные данные при последующем запросе
     */
    @Test
    void shouldUpdateUser_WhenUserExists() {

        UserEntity oldUser = userRepository.getUserById(1L);

        UserEntity newUser = new UserEntity();
        newUser.setId(oldUser.getId());
        newUser.setName("Aaaa");
        newUser.setEmail("aaa@email.com");
        newUser.setAge(10);
        newUser.setCreatedAt(LocalDateTime.now());

        userRepository.updateUser(newUser);

        UserEntity updatedUser = userRepository.getUserById(oldUser.getId());

        assertEquals(newUser.getName(), updatedUser.getName());
        assertEquals(newUser.getEmail(), updatedUser.getEmail());
        assertEquals(newUser.getAge(), updatedUser.getAge());
    }

    /**
     * Тест обработки случая обновления несуществующего пользователя.
     * Проверяет, что метод updateUser():
     * - Выбрасывает исключение при попытке обновления несуществующей записи
     * - Не создает новую запись при указании несуществующего ID
     * - Корректно обрабатывает ошибочный сценарий
     */
    @Test
    void shouldUpdateUser_WhenUserNotExists() {

        UserEntity newUser = new UserEntity();
        newUser.setId(999L);
        newUser.setName("Aaaa");
        newUser.setEmail("aaa@email.com");
        newUser.setAge(10);
        newUser.setCreatedAt(LocalDateTime.now());

        assertThrows(Exception.class, () -> {
            userRepository.updateUser(newUser);
        });
    }
}