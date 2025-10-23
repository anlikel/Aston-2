package org.example.repository;

import org.example.entities.User;
import org.example.exceptions.MyCustomException;
import org.example.util.UtilReader;
import org.hibernate.Session;

import java.util.List;

public class UserRepository implements UserDao {

    public Long saveUser(User user) {
        return TransactionManager.executeInTransaction((Session session) -> {
            Long userId = (Long) session.save(user);
            UtilReader.writeMessage("create user with id: " + userId);
            return userId;
        });
    }

    public User getUserById(Long id) {
        return TransactionManager.executeInTransaction((Session session) -> {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new MyCustomException("user with id " + id + " not found");
            }
            return user;
        });
    }

    public List<User> getAllUsers() {
        return TransactionManager.executeInTransaction((Session session) -> {
            return session.createQuery("FROM User", User.class).list();
        });
    }

    public void updateUser(User user) {
        TransactionManager.executeInTransaction((Session session) -> {
            session.update(user);
            UtilReader.writeMessage("User updated successfully");
        });
    }

    public void deleteUserById(Long id) {
        TransactionManager.executeInTransaction((Session session) -> {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new MyCustomException("User with id " + id + " not found");
            }
            session.delete(user);
            UtilReader.writeMessage("User deleted successfully");
        });
    }
}