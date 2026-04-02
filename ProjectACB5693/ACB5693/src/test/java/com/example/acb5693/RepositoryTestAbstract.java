package com.example.acb5693;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Абстрактный базовый класс для тестов репозитория с Testcontainers Настраивает PostgreSQL
 * контейнер для интеграционного тестирования
 */
@DataJpaTest
@Testcontainers
public abstract class RepositoryTestAbstract {

    // МЕНЯЕМ НА @Container и добавляем reuse
    @Container
    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("testdb")
        .withUsername("admin")
        .withPassword("admin")
        .withReuse(true); // ← ВАЖНО: переиспользование

    // УБИРАЕМ static блок со start() - @Container сам управляет

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
