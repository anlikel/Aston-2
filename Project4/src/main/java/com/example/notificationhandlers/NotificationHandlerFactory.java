package com.example.notificationhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Фабрика для получения обработчиков уведомлений по типу события.
 * Автоматически собирает все доступные обработчики и предоставляет их по запросу.
 */
@Component
public class NotificationHandlerFactory {

    private final Map<EventType, NotificationHandler> handlers;

    /**
     * Конструктор фабрики обработчиков уведомлений.
     * Автоматически собирает все бины NotificationHandler и создает карту по типам событий.
     *
     * @param handlerList список всех доступных обработчиков уведомлений
     */
    public NotificationHandlerFactory(List<NotificationHandler> handlerList) {
        handlers = handlerList.stream()
                .collect(Collectors.toMap(
                        NotificationHandler::getSupportedEventType,
                        handler -> handler
                ));
    }

    /**
     * Возвращает обработчик уведомлений для указанного типа события.
     *
     * @param eventType тип события, для которого требуется обработчик
     * @return обработчик уведомлений для указанного типа события
     * @throws IllegalArgumentException если обработчик для указанного типа события не найден
     */
    public NotificationHandler getHandler(EventType eventType) {
        NotificationHandler handler = handlers.get(eventType);
        if (handler == null) {
            throw new IllegalArgumentException("Обработчик для типа события не найден: " + eventType);
        }
        return handler;
    }
}