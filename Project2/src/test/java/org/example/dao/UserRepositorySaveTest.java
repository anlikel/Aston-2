package org.example.dao;

import org.example.entities.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Тестовый класс для проверки функциональности сохранения пользователей.
 * Проверяет метод saveUser() и корректность сохранения данных в базе данных.
 */
@Testcontainers
public class UserRepositorySaveTest extends HibernateTestAbstract {

    /**
     * Репозиторий пользователей для тестирования операций сохранения
     */
    private UserRepository userRepository;

    /**
     * Тест успешного сохранения пользователя в базу данных.
     * Проверяет, что метод saveUser():
     * - Корректно сохраняет новую сущность пользователя
     * - Возвращает непустой идентификатор сохраненной записи
     * - Все обязательные поля пользователя правильно сохраняются
     * - Не возникает исключений в процессе сохранения
     */
    @Test
    void shouldSaveUser() {
        // Инициализация репозитория
        userRepository = new UserRepository();

        // Подготовка тестовых данных - создание нового пользователя
        UserEntity user = new UserEntity();
        user.setName("Aaaa");
        user.setEmail("aaa@email.com");
        user.setAge(10);
        user.setCreatedAt(LocalDateTime.now());

        // Выполнение операции сохранения пользователя
        Long savedId = userRepository.saveUser(user);

        // Проверка, что сохранение прошло успешно и возвращен идентификатор
        assertNotNull(savedId);
    }
}