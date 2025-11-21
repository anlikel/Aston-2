package com.example.notificationhandlers;

import com.example.service.EmailService;
import org.springframework.stereotype.Component;

/**
 * Обработчик уведомлений для событий создания пользователя.
 * Отправляет приветственное письмо при создании нового пользователя.
 */
@Component
public class CreateUserNotificationHandler implements NotificationHandler {

    private final EmailService emailService;

    /**
     * Конструктор обработчика уведомлений для создания пользователя.
     *
     * @param emailService сервис для отправки электронной почты
     */
    public CreateUserNotificationHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Отправляет уведомление о создании пользователя.
     * Включает отправку приветственного письма и логирование операции.
     *
     * @param event событие создания пользователя
     */
    @Override
    public void sendNotification(ServiceEventDto event) {
        emailService.sendWelcomeEmail(event.getEmail(), event.getName());
        System.out.println("Приветственное уведомление отправлено: " + event.getEmail());
    }

    /**
     * Возвращает тип события, поддерживаемый данным обработчиком.
     *
     * @return тип события CREATE
     */
    @Override
    public EventType getSupportedEventType() {
        return EventType.CREATE;
    }
}