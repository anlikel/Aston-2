package org.example.repository;

import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.exceptions.MyCustomException;

public class TransactionManager {

    public static <T> T executeInTransaction(TransactionalOperation<T> operation) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            T result = operation.execute(session);
            transaction.commit();
            return result;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new MyCustomException(e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void executeInTransaction(TransactionalOperationVoid operation) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            operation.execute(session);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new MyCustomException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @FunctionalInterface
    public interface TransactionalOperation<T> {
        T execute(Session session);
    }

    @FunctionalInterface
    public interface TransactionalOperationVoid {
        void execute(Session session);
    }
}