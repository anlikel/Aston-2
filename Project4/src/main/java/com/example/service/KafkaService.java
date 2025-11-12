package com.example.service;

import com.example.entities.UserEntity;
import com.example.notificationhandlers.EventType;
import com.example.notificationhandlers.ServiceEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.user-topic:user-topic}")
    private String userTopic;

    public void sendUserEvent(UserEntity user, EventType eventType) {
        ServiceEventDto event = new ServiceEventDto(
                user.getEmail(),
                user.getName(),
                eventType
        );

        String key = user.getEmail();

        try {
            kafkaTemplate.send(userTopic, key, event)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("ORIGINAL KAFKA Successfully sent {} event for user: {}, partition: {}, offset: {}",
                                    eventType, user.getEmail(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        } else {
                            logger.error("ORIGINAL KAFKA Failed to send {} event for user: {}",
                                    eventType, user.getEmail(), ex);
                        }
                    });
        } catch (Exception e) {
            logger.error("ORIGINAL KAFKA Error sending Kafka message for user {}: {}", eventType, user.getEmail(), e);
        }
    }

    public void sendEmailOnUserCreate(UserEntity savedUser) {
        sendUserEvent(savedUser, EventType.CREATE);
    }

    public void sendEmailOnUserDelete(UserEntity deletedUser) {
        sendUserEvent(deletedUser, EventType.DELETE);
    }
}
