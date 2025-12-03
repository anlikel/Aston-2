package com.example.service;

/**
 * Интерфейс сервиса для обработки событий уведомлений по email через Kafka
 *
 * @param <T> тип DTO события
 */
public interface KafkaListenerEmailNotificationServiceInterface<T> {

    /**
     * Обрабатывает событие сервиса для отправки email уведомления
     *
     * @param eventDto DTO событие для обработки
     */
    void handleServiceEvent(T eventDto);
}