package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Главный класс приложения API Gateway
 *
 * <p>API Gateway - единая точка входа для всех клиентских запросов в микросервисной архитектуре.
 * Обрабатывает маршрутизацию, аутентификацию, мониторинг и другие cross-cutting concerns.</p>
 *
 * @SpringBootApplication - объединяет три аннотации:
 * - @Configuration: класс содержит конфигурации Spring Bean
 * - @ComponentScan: автоматическое сканирование компонентов в текущем пакете и подпакетах
 * - @EnableAutoConfiguration: автоматическая настройка Spring Boot на основе classpath
 * @EnableDiscoveryClient - включает регистрацию в сервисе обнаружения (Eureka)
 * Позволяет шлюзу находить другие микросервисы через Eureka Server
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    /**
     * Точка входа в приложение API Gateway
     *
     * <p>Запускает Spring Boot приложение с функциональностью API Gateway</p>
     *
     * @param args аргументы командной строки (необязательно)
     * @implNote По умолчанию запускается на порту 8080
     * @implNote Автоматически регистрируется в Eureka Server
     * @implNote Маршрутизация настраивается через application.yml/properties
     * @example Типичная конфигурация маршрутов:
     * - /api/users/** → user-service
     * - /api/orders/** → order-service
     * - /api/products/** → product-service
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
