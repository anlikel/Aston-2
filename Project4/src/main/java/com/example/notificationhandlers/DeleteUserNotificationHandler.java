package com.example.notificationhandlers;

import com.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserNotificationHandler implements NotificationHandler {

    @Autowired
    private EmailService emailService;

    @Override
    public void sendNotification(ServiceEventDto event) {
        emailService.sendAccountDeletionEmail(event.getEmail(), event.getName());
        System.out.println("Deletion notification sent to: " + event.getEmail());
    }

    @Override
    public EventType getSupportedEventType() {
        return EventType.DELETE;
    }
}