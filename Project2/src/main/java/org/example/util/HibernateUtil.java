package org.example.util;

import org.example.exceptions.MyCustomException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Утилитарный класс для работы с Hibernate SessionFactory.
 * Обеспечивает инициализацию и управление жизненным циклом SessionFactory.
 * Реализует паттерн Singleton для единой точки доступа к SessionFactory.
 */
public class HibernateUtil {

    /**
     * Единый экземпляр SessionFactory, инициализируемый при загрузке класса.
     */
    private static final SessionFactory sf = buildSessionFactory();

    /**
     * Создает и настраивает SessionFactory на основе конфигурации Hibernate.
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
     * Возвращает экземпляр SessionFactory.
     *
     * @return единый экземпляр SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sf;
    }

    /**
     * Закрывает SessionFactory и освобождает ресурсы.
     * Должен вызываться при завершении работы приложения.
     */
    public static void closeSessionFactory() {
        getSessionFactory().close();
    }
}