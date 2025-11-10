package com.example.notificationhandlers;

public interface NotificationHandler {
    void sendNotification(ServiceEventDto event);
    EventType getSupportedEventType();
}
