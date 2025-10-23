package org.example.repository;

import org.example.entities.User;
import org.example.exceptions.MyCustomException;
import org.example.util.HibernateUtil;
import org.example.util.UtilReader;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserRepository implements UserDao{

    public Long saveUser(User user){
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        Long userId=null;

        try{
            transaction = session.beginTransaction();
            userId = (Long) session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw new MyCustomException("cant save user,  transaction rollback");
            }
        }
        finally{session.close();}
        return userId;
    }

    public User getUserById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = null;

        try {
            user = session.get(User.class, id);
        } finally {
            session.close();
        }
        return user;
    }

    public List<User> getAllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> users = null;

        try {
            users = session.createQuery("FROM User", User.class).list();
        } finally {
            session.close();
        }
        return users;
    }

    public void updateUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new MyCustomException("cant update user,  transaction rollback");
        } finally {
            session.close();
        }
    }

    public void deleteUserById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if(user==null){
                throw new Exception();
            }
            else if (user != null) {
                session.delete(user);
            }
            transaction.commit();
            UtilReader.writeMessage("user deleted");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new MyCustomException("cant delete user,  transaction rollback");
        } finally {
            session.close();
        }
    }

}
