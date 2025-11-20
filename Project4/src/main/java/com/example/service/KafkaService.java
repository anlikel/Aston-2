package com.example.service;

import com.example.entities.UserEntity;
import com.example.notificationhandlers.EventType;
import com.example.notificationhandlers.ServiceEventDto;
import com.example.serviceinterfaces.KafkaServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Сервис для отправки событий в Kafka.
 * Обеспечивает отправку уведомлений о событиях пользователя (создание, удаление).
 */
@Service
public class KafkaService implements KafkaServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.user-topic:user-topic}")
    private String userTopic;

    /**
     * Отправляет событие пользователя в Kafka.
     *
     * @param user      сущность пользователя, для которого генерируется событие
     * @param eventType тип события (CREATE или DELETE)
     */
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

    /**
     * Отправляет событие создания пользователя в Kafka.
     *
     * @param savedUser сущность созданного пользователя
     */
    public void sendEmailOnUserCreate(UserEntity savedUser) {
        sendUserEvent(savedUser, EventType.CREATE);
    }

    /**
     * Отправляет событие удаления пользователя в Kafka.
     *
     * @param deletedUser сущность удаленного пользователя
     */
    public void sendEmailOnUserDelete(UserEntity deletedUser) {
        sendUserEvent(deletedUser, EventType.DELETE);
    }
}