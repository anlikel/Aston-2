package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Главный класс приложения Spring Cloud Config Server
 *
 * <p>Config Server - централизованный сервер конфигурации для микросервисов.
 * Позволяет хранить настройки всех микросервисов в одном месте (Git, файловая система и т.д.).</p>
 *
 * @SpringBootApplication - объединяет три аннотации:
 * - @Configuration: класс содержит конфигурации Spring Bean
 * - @ComponentScan: автоматическое сканирование компонентов в текущем пакете и подпакетах
 * - @EnableAutoConfiguration: автоматическая настройка Spring Boot на основе classpath
 * @EnableConfigServer - включает функциональность Spring Cloud Config Server
 * Позволяет этому приложению работать как сервер конфигурации
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    /**
     * Точка входа в приложение Spring Cloud Config Server
     *
     * <p>Запускает Spring Boot приложение с конфигурацией Config Server</p>
     *
     * @param args аргументы командной строки (необязательно)
     * @implNote По умолчанию запускается на порту 8888
     * @implNote Для работы требует настройки хранилища конфигураций (Git, SVN, локальные файлы)
     * @example После запуска конфигурации доступны по URL:
     * - http://localhost:8088/{application}/{profile}[/{label}]
     * - http://localhost:8088/{application}-{profile}.yml
     * - http://localhost:8088/{label}/{application}-{profile}.yml
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
