package org.example.dao;

import org.example.entities.UserEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Абстрактный базовый класс для тестирования Hibernate с использованием Testcontainers.
 * Предоставляет общую конфигурацию и методы для работы с тестовой базой данных PostgreSQL.
 */
public abstract class HibernateTestAbstract {
    /**
     * Фабрика сессий Hibernate для тестов
     */
    protected static SessionFactory sessionFactory;

    /**
     * Контейнер PostgreSQL для тестов.
     * Используется для создания изолированной тестовой базы данных.
     */
    @Container
    protected static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("postgres")
                    .withUsername("user")
                    .withPassword("1234")
                    .withReuse(true);

    /**
     * Настройка тестового окружения перед всеми тестами.
     * Конфигурирует Hibernate SessionFactory с параметрами тестовой БД.
     */
    @BeforeAll
    static void setUpAll() {

        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "none");
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.format_sql", "true");

        configuration.addAnnotatedClass(UserEntity.class);
        sessionFactory = configuration.buildSessionFactory();
        HibernateUtil.setTestSessionFactory(sessionFactory);
    }

    /**
     * Очистка ресурсов после выполнения всех тестов.
     * Закрывает фабрику сессий и останавливает контейнер с БД.
     */
    @AfterAll
    static void tearDownAll() {
        HibernateUtil.clearTestSessionFactory();
        HibernateUtil.closeSessionFactory();
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
        postgres.stop();
    }

    /**
     * Инициализация базы данных перед каждым тестом.
     * Выполняет SQL-скрипт для создания начальной структуры данных.
     */
    @BeforeEach
    void initDB() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            executeSqlScript(session, "init-scripts/init.sql");

            session.getTransaction().commit();
        } catch (IOException e) {
            throw new RuntimeException("Failed to execute setup scripts", e);
        }
    }

    /**
     * Очистка базы данных после каждого теста.
     * Выполняет SQL-скрипт для удаления тестовых данных.
     */
    @AfterEach
    void clearDB() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            executeSqlScript(session, "init-scripts/cleanup.sql");

            session.getTransaction().commit();
        } catch (IOException e) {
            throw new RuntimeException("Failed to execute setup scripts", e);
        }
    }

    /**
     * Выполняет SQL-скрипт в текущей сессии Hibernate.
     *
     * @param session    сессия Hibernate для выполнения запросов
     * @param scriptPath путь к SQL-скрипту относительно classpath
     * @throws IOException      если файл скрипта не найден или недоступен
     * @throws RuntimeException если файл скрипта не существует
     */
    private void executeSqlScript(Session session, String scriptPath) throws IOException {
        Path path = Paths.get(scriptPath);
        if (!Files.exists(path)) {
            throw new RuntimeException("Script file not found: " + scriptPath);
        }

        String sqlScript = Files.readString(path);
        String[] sqlCommands = sqlScript.split(";");

        for (String command : sqlCommands) {
            String trimmedCommand = command.trim();
            if (!trimmedCommand.isEmpty() && !trimmedCommand.startsWith("--")) {
                session.createNativeMutationQuery(trimmedCommand).executeUpdate();
            }
        }
    }
}