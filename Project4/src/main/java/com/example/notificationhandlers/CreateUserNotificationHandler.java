package com.example.notificationhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateUserNotificationHandler implements NotificationHandler {

    @Autowired
    private EmailService emailService;

    @Override
    public void sendNotification(ServiceEventDto event) {
        emailService.sendWelcomeEmail(event.getEmail(), event.getName());
        System.out.println("Welcome notification sent to: " + event.getEmail());
    }

    @Override
    public EventType getSupportedEventType() {
        return EventType.CREATE;
    }
}