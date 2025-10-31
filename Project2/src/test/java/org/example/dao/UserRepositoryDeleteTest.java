package org.example.dao;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки функциональности удаления пользователей.
 * Проверяет методы удаления и связанную с ними бизнес-логику.
 */
@Testcontainers
public class UserRepositoryDeleteTest extends HibernateTestAbstract {

    /**
     * Репозиторий пользователей для тестирования
     */
    private UserRepository userRepository = new UserRepository();

    /**
     * Тест успешного удаления пользователя по идентификатору.
     * Проверяет, что при существующем пользователе метод deleteUserById:
     * - Корректно удаляет пользователя
     * - Возвращает правильные данные удаленного пользователя
     */
    @Test
    void shouldDeleteUserById_WhenUserExists() {

        UserEntity deletedUser = userRepository.deleteUserById(1L);

        assertEquals("User1", deletedUser.getName());
        assertEquals("mail1@example.com", deletedUser.getEmail());
        assertEquals(25, deletedUser.getAge());
    }

    /**
     * Тест обработки случая, когда пользователь не существует.
     * Проверяет, что при попытке получения несуществующего пользователя:
     * - Выбрасывается исключение MyCustomException
     * - Исключение корректно обрабатывается
     */
    @Test
    void shouldThrowException_WhenUserNotExists() {

        Long wrongId = 4L;

        assertThrows(MyCustomException.class, () -> {
            userRepository.getUserById(wrongId);
        });
    }
}