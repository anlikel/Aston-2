package org.example.dao;

import org.example.entities.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Тестовый класс для проверки функциональности получения всех пользователей.
 * Проверяет метод getAllUsers() в различных сценариях наличия данных.
 */
public class UserRepositoryFindAllTest extends HibernateTestAbstract {

    /** Репозиторий пользователей для тестирования */
    private UserRepository userRepository = new UserRepository();

    /**
     * Тест получения всех пользователей, когда в базе данных есть пользователи.
     * Проверяет, что метод getAllUsers():
     * - Корректно возвращает всех существующих пользователей
     * - Возвращает правильное количество пользователей
     * - Не пропускает никакие записи
     */
    @Test
    void shouldFindAllUsers_IfAnyUsersAreInDb() {
        // Выполнение операции получения всех пользователей
        List<UserEntity> users = userRepository.getAllUsers();

        // Проверка, что возвращено правильное количество пользователей
        assertEquals(2, users.size());
    }

    /**
     * Тест получения всех пользователей, когда база данных пуста.
     * Проверяет, что метод getAllUsers():
     * - Корректно возвращает пустой список при отсутствии пользователей
     * - Не выбрасывает исключений при пустой базе данных
     * - Возвращает пустую коллекцию вместо null
     */
    @Test
    void shouldReturnEmptyList_WhenNoUsersInDB() {
        // Подготовка: удаление всех пользователей из базы данных
        userRepository.deleteUserById(1L);
        userRepository.deleteUserById(2L);

        // Выполнение операции получения всех пользователей
        List<UserEntity> users = userRepository.getAllUsers();

        // Проверка, что возвращен пустой список
        assertEquals(0, users.size());
    }
}