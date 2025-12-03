package com.example.service;

import com.example.EventType;
import com.example.ServiceEventDto;
import com.example.entities.UserEntity;

import com.example.serviceinterfaces.KafkaServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Фейковый сервис Kafka для тестирования и разработки.
 * Имитирует отправку событий в Kafka путем публикации Spring событий внутри приложения.
 */
@Service
public class FakeKafkaService implements KafkaServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(FakeKafkaService.class);

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Конструктор для внедрения зависимости ApplicationEventPublisher.
     *
     * @param eventPublisher издатель событий Spring для публикации событий внутри приложения
     */
    public FakeKafkaService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Отправляет событие пользователя через фейковый Kafka сервис.
     * Вместо отправки в реальный Kafka публикует событие внутри приложения через Spring Event Publisher.
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
        logger.info("FAKE KAFKA Successfully sent {} event for user: {}", eventType, user.getEmail());
        eventPublisher.publishEvent(event);
    }

    /**
     * Отправляет событие создания пользователя через фейковый Kafka сервис.
     *
     * @param savedUser сущность созданного пользователя
     */
    public void sendEmailOnUserCreate(UserEntity savedUser) {
        sendUserEvent(savedUser, EventType.CREATE);
    }

    /**
     * Отправляет событие удаления пользователя через фейковый Kafka сервис.
     *
     * @param deletedUser сущность удаленного пользователя
     */
    public void sendEmailOnUserDelete(UserEntity deletedUser) {
        sendUserEvent(deletedUser, EventType.DELETE);
    }
}