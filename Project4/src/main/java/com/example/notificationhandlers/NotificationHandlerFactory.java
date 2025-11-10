package com.example.notificationhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NotificationHandlerFactory {

    private final Map<EventType, NotificationHandler> handlers;

    @Autowired
    public NotificationHandlerFactory(List<NotificationHandler> handlerList) {
        handlers = handlerList.stream()
                .collect(Collectors.toMap(
                        NotificationHandler::getSupportedEventType,
                        handler -> handler
                ));
    }

    public NotificationHandler getHandler(EventType eventType) {
        NotificationHandler handler = handlers.get(eventType);
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for event type: " + eventType);
        }
        return handler;
    }
}
