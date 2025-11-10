package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendWelcomeEmail(String email, String name) {
        String subject = "Добро пожаловать!";
        String message = String.format(
                "Уважаемый %s, ваш аккаунт был успешно создан!",
                name
        );
        sendEmail(email, subject, message);
    }

    public void sendAccountDeletionEmail(String email, String name) {
        String subject = "Ваш аккаунт был удален";
        String message = String.format(
                "Уважаемый %s, ваш аккаунт был удален из нашей системы.",
                name
        );
        sendEmail(email, subject, message);
    }

    private void sendEmail(String to, String subject, String message) {
        System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
        System.out.println("---");
    }
}
