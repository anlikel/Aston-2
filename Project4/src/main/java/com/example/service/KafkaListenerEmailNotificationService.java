package com.example.service;

import com.example.notificationhandlers.NotificationHandler;
import com.example.notificationhandlers.NotificationHandlerFactory;
import com.example.notificationhandlers.ServiceEventDto;
import com.example.serviceinterfaces.KafkaListenerEmailNotificationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Сервис для обработки событий из Kafka и отправки уведомлений.
 * Слушает события пользователей и делегирует их обработку соответствующим обработчикам.
 */
@Service
public class KafkaListenerEmailNotificationService implements KafkaListenerEmailNotificationServiceInterface<ServiceEventDto> {

    @Autowired
    private NotificationHandlerFactory handlerFactory;

    /**
     * Обрабатывает события сервиса, полученные из Kafka топика.
     * Получает соответствующий обработчик на основе типа события и отправляет уведомление.
     *
     * @param event событие сервиса, содержащее информацию о пользователе и типе события
     */
    @KafkaListener(topics = "#{'${app.kafka.topics.user-topic:user-topic}'}")
    public void handleServiceEvent(ServiceEventDto event) {
        System.out.println("Received event: " + event.getEventType() + " for user: " + event.getEmail());

        NotificationHandler handler = handlerFactory.getHandler(event.getEventType());

        handler.sendNotification(event);
    }
}