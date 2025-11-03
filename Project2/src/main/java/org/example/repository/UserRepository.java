package org.example.repository;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.util.UtilReader;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Репозиторий для работы с пользователями в базе данных.
 * Реализует интерфейс UserDao используя Hibernate и транзакционный менеджер.
 * Обеспечивает персистентность данных пользователей с логированием операций.
 */
public class UserRepository implements UserDao {

    /**
     * Логгер для записи событий работы с базой данных.
     */
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    /**
     * Сохраняет пользователя в базе данных.
     *
     * @param user объект пользователя для сохранения
     * @return идентификатор сохраненного пользователя
     * @throws MyCustomException если произошла ошибка при сохранении
     */
    public Long saveUser(UserEntity user) {
        logger.info("DB event:try to saveUser {}", user.getName());
        return TransactionManager.executeInTransaction((Session session) -> {
            Long userId = (Long) session.save(user);
            UtilReader.writeMessage("create user with id: " + userId);
            logger.info("DB event:success saveUser {}", user.getName());
            return userId;
        });
    }

    /**
     * Получает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект пользователя
     * @throws MyCustomException если пользователь с указанным id не найден
     */
    public UserEntity getUserById(Long id) {
        logger.info("DB event:try to findUser id={}", id);
        return TransactionManager.executeInTransaction((Session session) -> {
            UserEntity user = session.get(UserEntity.class, id);
            if (user == null) {
                logger.error("DB event:failed to findUser id={}", id);
                throw new MyCustomException("user with id " + id + " not found");
            }
            logger.info("DB event:success findUser id={}", id);
            return user;
        });
    }

    /**
     * Получает список всех пользователей из базы данных.
     *
     * @return список всех пользователей
     */
    public List<UserEntity> getAllUsers() {
        logger.info("DB event:try to getUsersList");
        return TransactionManager.executeInTransaction((Session session) -> {
            logger.info("DB event:success getUsersList");
            return session.createQuery("FROM UserEntity", UserEntity.class).list();
        });
    }

    /**
     * Обновляет данные пользователя в базе данных.
     *
     * @param user объект пользователя с обновленными данными
     * @throws MyCustomException если произошла ошибка при обновлении
     */
    public void updateUser(UserEntity user) {
        logger.info("DB event:try to updateUser id={}", user.getId());
        TransactionManager.executeInTransaction((Session session) -> {
            session.update(user);
            UtilReader.writeMessage("User updated successfully");
            logger.info("DB event:success updateUser id={}", user.getId());
        });
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя для удаления
     * @return объект удаленного пользователя
     * @throws MyCustomException если пользователь с указанным id не найден
     */
    public UserEntity deleteUserById(Long id) {
        logger.info("DB event:try to deleteUser id={}", id);
        return TransactionManager.executeInTransaction((Session session) -> {
            UserEntity user = session.get(UserEntity.class, id);
            if (user == null) {
                logger.error("DB event:failed to deleteUser id={}", id);
                throw new MyCustomException("User with id " + id + " not found");
            }
            session.delete(user);
            UtilReader.writeMessage("User deleted successfully");
            logger.info("DB event:success deleteUser id={}", id);
            return user;
        });
    }
}