package com.example.service;

import org.springframework.stereotype.Service;

/**
 * Сервис для отправки электронных писем.
 * Предоставляет методы для отправки приветственных писем и уведомлений об удалении аккаунта.
 */
@Service
public class EmailService {

    /**
     * Отправляет приветственное письмо новому пользователю.
     *
     * @param email адрес электронной почты получателя
     * @param name  имя пользователя для персонализации письма
     */
    public void sendWelcomeEmail(String email, String name) {
        String subject = "Добро пожаловать!";
        String message = String.format(
                "Уважаемый %s, ваш аккаунт был успешно создан!",
                name
        );
        sendEmail(email, subject, message);
    }

    /**
     * Отправляет уведомление об удалении аккаунта.
     *
     * @param email адрес электронной почты получателя
     * @param name  имя пользователя для персонализации письма
     */
    public void sendAccountDeletionEmail(String email, String name) {
        String subject = "Ваш аккаунт был удален";
        String message = String.format(
                "Уважаемый %s, ваш аккаунт был удален из нашей системы.",
                name
        );
        sendEmail(email, subject, message);
    }

    /**
     * Внутренний метод для отправки электронного письма.
     * В текущей реализации выводит информацию о письме в консоль.
     *
     * @param to      адрес получателя
     * @param subject тема письма
     * @param message содержание письма
     */
    private void sendEmail(String to, String subject, String message) {
        System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
        System.out.println("---");
    }
}