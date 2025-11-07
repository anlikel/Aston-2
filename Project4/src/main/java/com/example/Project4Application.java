package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения для запуска Spring Boot приложения.
 * Содержит точку входа в приложение и конфигурацию Spring Boot.
 */
@SpringBootApplication
public class Project4Application {

    /**
     * Основной метод для запуска Spring Boot приложения.
     * Инициализирует Spring контекст и запускает встроенный сервер приложений.
     *
     * @param args аргументы командной строки, передаваемые при запуске приложения
     */
    public static void main(String[] args) {
        SpringApplication.run(Project4Application.class, args);
    }

}