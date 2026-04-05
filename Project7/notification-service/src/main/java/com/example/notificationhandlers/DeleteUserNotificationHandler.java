package com.example.notificationhandlers;

import com.example.EventType;
import com.example.ServiceEventDto;
import com.example.service.EmailService;
import org.springframework.stereotype.Component;

/**
 * Обработчик уведомлений для событий удаления пользователя.
 * Отправляет письмо об удалении аккаунта при удалении пользователя.
 */
@Component
public class DeleteUserNotificationHandler implements NotificationHandler {

    private final EmailService emailService;

    /**
     * Конструктор обработчика уведомлений для удаления пользователя.
     *
     * @param emailService сервис для отправки электронной почты
     */
    public DeleteUserNotificationHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Отправляет уведомление об удалении пользователя.
     * Включает отправку письма об удалении аккаунта и логирование операции.
     *
     * @param event событие удаления пользователя
     */
    @Override
    public void sendNotification(ServiceEventDto event) {
        emailService.sendAccountDeletionEmail(event.getEmail(), event.getName());
        System.out.println("Уведомление об удалении отправлено: " + event.getEmail());
    }

    /**
     * Возвращает тип события, поддерживаемый данным обработчиком.
     *
     * @return тип события DELETE
     */
    @Override
    public EventType getSupportedEventType() {
        return EventType.DELETE;
    }
}