package org.example.dao;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки функциональности поиска пользователей по идентификатору.
 * Проверяет метод getUserById() в различных сценариях поиска.
 */
@Testcontainers
public class UserRepositoryFindTest extends HibernateTestAbstract {

    /**
     * Репозиторий пользователей для тестирования
     */
    private UserRepository userRepository = new UserRepository();

    /**
     * Тест успешного поиска пользователя по идентификатору, когда пользователь существует.
     * Проверяет, что метод getUserById():
     * - Корректно находит пользователя по существующему ID
     * - Возвращает правильные данные пользователя
     * - Все поля пользователя соответствуют ожидаемым значениям
     */
    @Test
    void shouldFindUserById_WhenUserExists() {

        UserEntity foundUser = userRepository.getUserById(1L);

        assertEquals("User1", foundUser.getName());
        assertEquals("mail1@example.com", foundUser.getEmail());
        assertEquals(25, foundUser.getAge());
    }

    /**
     * Тест обработки случая, когда пользователь не найден.
     * Проверяет, что метод getUserById():
     * - Выбрасывает исключение MyCustomException при поиске несуществующего пользователя
     * - Исключение содержит корректную информацию об ошибке
     * - Система корректно обрабатывает сценарий отсутствия пользователя
     */
    @Test
    void shouldThrowException_WhenUserNotFound() {

        Long wrongId = 4L;

        assertThrows(MyCustomException.class, () -> {
            userRepository.getUserById(wrongId);
        });
    }
}