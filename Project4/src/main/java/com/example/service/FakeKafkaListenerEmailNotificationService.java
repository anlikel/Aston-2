package com.example.service;

import com.example.notificationhandlers.NotificationHandler;
import com.example.notificationhandlers.NotificationHandlerFactory;
import com.example.notificationhandlers.ServiceEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class FakeKafkaListenerEmailNotificationService {

    @Autowired
    private NotificationHandlerFactory handlerFactory;

    @EventListener
    public void handleServiceEvent(ServiceEventDto event) {
        System.out.println("Received event: " + event.getEventType() + " for user: " + event.getEmail());

        NotificationHandler handler = handlerFactory.getHandler(event.getEventType());

        handler.sendNotification(event);
    }
}
