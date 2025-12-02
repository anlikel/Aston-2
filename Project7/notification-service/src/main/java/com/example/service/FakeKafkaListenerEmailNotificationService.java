package com.example.service;

import com.example.notificationhandlers.NotificationHandler;
import com.example.notificationhandlers.NotificationHandlerFactory;
import com.example.notificationhandlers.ServiceEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Фейковый сервис для обработки событий и отправки уведомлений.
 * Слушает Spring события вместо Kafka и делегирует их обработку соответствующим обработчикам.
 */
@Service
public class FakeKafkaListenerEmailNotificationService implements KafkaListenerEmailNotificationServiceInterface<ServiceEventDto> {

    @Autowired
    private NotificationHandlerFactory handlerFactory;

    /**
     * Обрабатывает события сервиса, полученные через Spring EventListener.
     * Получает соответствующий обработчик на основе типа события и отправляет уведомление.
     *
     * @param event событие сервиса, содержащее информацию о пользователе и типе события
     */
    @EventListener
    public void handleServiceEvent(ServiceEventDto event) {
        System.out.println("Received event: " + event.getEventType() + " for user: " + event.getEmail());

        NotificationHandler handler = handlerFactory.getHandler(event.getEventType());

        handler.sendNotification(event);
    }
}