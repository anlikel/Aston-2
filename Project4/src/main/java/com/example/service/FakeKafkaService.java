package com.example.service;

import com.example.entities.UserEntity;
import com.example.notificationhandlers.EventType;
import com.example.notificationhandlers.ServiceEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class FakeKafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    private final ApplicationEventPublisher eventPublisher;

    public FakeKafkaService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void sendUserEvent(UserEntity user, EventType eventType) {
        ServiceEventDto event = new ServiceEventDto(
                user.getEmail(),
                user.getName(),
                eventType
        );
        logger.info("Successfully sent {} event for user: {}", eventType, user.getEmail());
        eventPublisher.publishEvent(event);
    }

    public void sendEmailOnUserCreate(UserEntity savedUser) {
        sendUserEvent(savedUser, EventType.CREATE);
    }

    public void sendEmailOnUserDelete(UserEntity deletedUser) {
        sendUserEvent(deletedUser, EventType.DELETE);
    }
}
