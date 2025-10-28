package org.example.dao;

import org.example.entities.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class HibernateTestAbstract {
    protected static SessionFactory sessionFactory;
    protected static PostgreSQLContainer<?> postgres;

    @BeforeAll
    static void setUpAll() {

        postgres =
                new PostgreSQLContainer<>("postgres:15")
                        .withDatabaseName("postgres")
                        .withUsername("user")
                        .withPassword("1234");
        postgres.start();

        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "none");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");

        configuration.addAnnotatedClass(UserEntity.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    @AfterAll
    static void tearDownAll() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        if (postgres != null) {
            postgres.stop();
        }
    }

    @BeforeEach
    void setUp() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            executeSqlScript(session, "init-scripts/init.sql");

            executeSqlScript(session, "init-scripts/cleanup.sql");

            session.getTransaction().commit();
        } catch (IOException e) {
            throw new RuntimeException("Failed to execute setup scripts", e);
        }
    }

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
