package com.example.notificationhandlers;

/**
 * Интерфейс для обработчиков уведомлений.
 * Определяет методы для отправки уведомлений и получения поддерживаемого типа события.
 */
public interface NotificationHandler {

    /**
     * Отправляет уведомление на основе переданного события.
     *
     * @param event событие, на основе которого формируется уведомление
     */
    void sendNotification(ServiceEventDto event);

    /**
     * Возвращает тип события, который поддерживается данным обработчиком.
     *
     * @return тип события, поддерживаемый обработчиком
     */
    EventType getSupportedEventType();
}