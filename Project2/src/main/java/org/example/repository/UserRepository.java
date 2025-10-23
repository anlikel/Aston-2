package org.example.repository;

import org.example.entities.User;
import org.example.exceptions.MyCustomException;
import org.example.util.UtilReader;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserRepository implements UserDao {

    private static final Logger logger=LoggerFactory.getLogger(UserRepository.class);

    public Long saveUser(User user) {
        logger.info("DB event:try to saveUser{}", user.getName());
        return TransactionManager.executeInTransaction((Session session) -> {
            Long userId = (Long) session.save(user);
            UtilReader.writeMessage("create user with id: " + userId);
            logger.info("DB event:success saveUser{}", user.getName());
            return userId;
        });
    }

    public User getUserById(Long id) {
        logger.info("DB event:try to findUser{}", id);
        return TransactionManager.executeInTransaction((Session session) -> {
            User user = session.get(User.class, id);
            if (user == null) {
                logger.error("DB event:failed to findUser{}", id);
                throw new MyCustomException("user with id " + id + " not found");
            }
            logger.info("DB event:success findUser{}", id);
            return user;
        });
    }

    public List<User> getAllUsers() {
        logger.info("DB event:try to getUsersList");
        return TransactionManager.executeInTransaction((Session session) -> {
            logger.info("DB event:success getUsersList");
            return session.createQuery("FROM User", User.class).list();
        });
    }

    public void updateUser(User user) {
        logger.info("DB event:try to updateUser{}", user.getId());
        TransactionManager.executeInTransaction((Session session) -> {
            session.update(user);
            UtilReader.writeMessage("User updated successfully");
            logger.info("DB event:success updateUser{}", user.getId());
        });
    }

    public void deleteUserById(Long id) {
        logger.info("DB event:try to deleteUser{}", id);
        TransactionManager.executeInTransaction((Session session) -> {
            User user = session.get(User.class, id);
            if (user == null) {
                logger.error("DB event:failed to deleteUser{}", id);
                throw new MyCustomException("User with id " + id + " not found");
            }
            session.delete(user);
            UtilReader.writeMessage("User deleted successfully");
            logger.info("DB event:success deleteUser{}", id);
        });
    }
}