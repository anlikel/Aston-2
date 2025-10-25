package org.example.repository;

import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.exceptions.MyCustomException;

/**
 * Менеджер транзакций для работы с Hibernate.
 * Предоставляет методы для выполнения операций в транзакционном контексте.
 * Обеспечивает автоматическое управление жизненным циклом транзакций и сессий.
 */
public class TransactionManager {

    /**
     * Выполняет операцию с возвращаемым значением в транзакционном контексте.
     * Автоматически управляет открытием/закрытием сессии и коммитом/откатом транзакции.
     *
     * @param <T>       тип возвращаемого значения
     * @param operation операция для выполнения в контексте сессии
     * @return результат выполнения операции
     * @throws MyCustomException если произошла ошибка во время выполнения операции
     */
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

    /**
     * Выполняет операцию без возвращаемого значения в транзакционном контексте.
     * Автоматически управляет открытием/закрытием сессии и коммитом/откатом транзакции.
     *
     * @param operation операция для выполнения в контексте сессии
     * @throws MyCustomException если произошла ошибка во время выполнения операции
     */
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

    /**
     * Функциональный интерфейс для операций с возвращаемым значением.
     *
     * @param <T> тип возвращаемого значения
     */
    @FunctionalInterface
    public interface TransactionalOperation<T> {
        /**
         * Выполняет операцию в контексте Hibernate сессии.
         *
         * @param session Hibernate сессия
         * @return результат операции
         */
        T execute(Session session);
    }

    /**
     * Функциональный интерфейс для операций без возвращаемого значения.
     */
    @FunctionalInterface
    public interface TransactionalOperationVoid {
        /**
         * Выполняет операцию в контексте Hibernate сессии.
         *
         * @param session Hibernate сессия
         */
        void execute(Session session);
    }
}