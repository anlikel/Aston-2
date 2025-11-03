package org.example.util;

import org.example.exceptions.MyCustomException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Утилитарный класс для работы с Hibernate SessionFactory.
 * Обеспечивает инициализацию и управление жизненным циклом SessionFactory.
 * Реализует паттерн Singleton для единой точки доступа к SessionFactory.
 * Поддерживает тестовый режим для использования в unit-тестах с testcontainers.
 */
public class HibernateUtil {

    /**
     * Production экземпляр SessionFactory, инициализируемый лениво.
     */
    private static SessionFactory sessionFactory;

    /**
     * Тестовый экземпляр SessionFactory для использования в unit-тестах.
     */
    private static SessionFactory testSessionFactory;

    /**
     * Флаг указывающий, что используется тестовый режим.
     */
    private static boolean testMode = false;

    /**
     * Создает и настраивает SessionFactory на основе конфигурации Hibernate.
     * Конфигурация загружается из hibernate.cfg.xml.
     *
     * @return сконфигурированный экземпляр SessionFactory
     * @throws MyCustomException если не удалось создать SessionFactory
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new MyCustomException("cannot build session factory");
        }
    }

    /**
     * Устанавливает тестовую SessionFactory для использования в unit-тестах.
     * После вызова этого метода все запросы к getSessionFactory() будут возвращать тестовую фабрику.
     *
     * @param factory тестовая SessionFactory
     */
    public static void setTestSessionFactory(SessionFactory factory) {
        testMode = true;
        testSessionFactory = factory;
    }

    /**
     * Очищает тестовую SessionFactory и переключает в production режим.
     * Должен вызываться после завершения тестов.
     */
    public static void clearTestSessionFactory() {
        testMode = false;
        testSessionFactory = null;
    }

    /**
     * Возвращает экземпляр SessionFactory.
     * В тестовом режиме возвращает тестовую SessionFactory, иначе - production.
     *
     * @return экземпляр SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        if (testMode && testSessionFactory != null) {
            return testSessionFactory;
        }

        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    /**
     * Закрывает SessionFactory и освобождает ресурсы.
     * Должен вызываться при завершении работы приложения.
     * Закрывает как production, так и тестовую SessionFactory.
     */
    public static void closeSessionFactory() {
        if (testMode && testSessionFactory != null) {
            testSessionFactory.close();
            testSessionFactory = null;
        }
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}