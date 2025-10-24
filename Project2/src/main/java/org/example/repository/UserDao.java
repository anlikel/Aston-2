package org.example.repository;

import org.example.entities.User;

import java.util.List;

/**
 * Data Access Object (DAO) интерфейс для работы с пользователями.
 * Определяет контракт для операций CRUD (Create, Read, Update, Delete) с сущностями User.
 * Реализации этого интерфейса обеспечивают персистентность данных пользователей.
 */
public interface UserDao {

    /**
     * Сохраняет пользователя в хранилище данных.
     *
     * @param user объект пользователя для сохранения
     * @return идентификатор сохраненного пользователя
     */
    public Long saveUser(User user);

    /**
     * Получает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект пользователя или null, если пользователь не найден
     */
    public User getUserById(Long id);

    /**
     * Получает список всех пользователей из хранилища.
     *
     * @return список всех пользователей, может быть пустым
     */
    public List<User> getAllUsers();

    /**
     * Обновляет данные пользователя в хранилище.
     *
     * @param user объект пользователя с обновленными данными
     */
    public void updateUser(User user);

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя для удаления
     */
    public void deleteUserById(Long id);
}