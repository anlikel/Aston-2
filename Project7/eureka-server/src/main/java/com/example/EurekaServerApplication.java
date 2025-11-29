package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Главный класс приложения Eureka Server
 *
 * <p>Eureka Server - это сервер обнаружения микросервисов, который позволяет
 * микросервисам регистрироваться и обнаруживать друг друга.</p>
 *
 * @SpringBootApplication - объединяет три аннотации:
 * - @Configuration: определяет класс как источник конфигурации
 * - @ComponentScan: включает сканирование компонентов в текущем пакете
 * - @EnableAutoConfiguration: включает автоматическую настройку Spring Boot
 * @EnableEurekaServer - включает Eureka Server functionality
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    /**
     * Главный метод приложения
     *
     * <p>Запускает Spring Boot приложение с конфигурацией Eureka Server</p>
     *
     * @param args аргументы командной строки
     * @implNote Использует встроенный Tomcat сервер на порту 8761 по умолчанию
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
