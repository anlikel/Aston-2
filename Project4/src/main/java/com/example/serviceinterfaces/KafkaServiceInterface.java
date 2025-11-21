package com.example.serviceinterfaces;

import com.example.entities.UserEntity;
import com.example.notificationhandlers.EventType;

/**
 * Интерфейс сервиса для работы с Apache Kafka.
 * Определяет контракт для отправки событий, связанных с пользователями, в Kafka топики.
 *
 * <p>Данный интерфейс предоставляет методы для асинхронной отправки уведомлений и событий
 * через брокер сообщений Kafka, что позволяет реализовать event-driven архитектуру
 * и декомпозировать систему на слабосвязанные компоненты.</p>
 *
 * <p><strong>Основные функции:</strong>
 * <ul>
 *   <li>Отправка пользовательских событий с указанием типа события</li>
 *   <li>Отправка email-уведомлений при создании пользователя</li>
 *   <li>Отправка email-уведомлений при удалении пользователя</li>
 * </ul>
 *
 * @implSpec Реализации должны гарантировать доставку сообщений и обработку ошибок Kafka
 * @see org.springframework.kafka.core.KafkaTemplate
 * @see EventType
 * @see UserEntity
 */
public interface KafkaServiceInterface {

    /**
     * Отправляет событие, связанное с пользователем, в соответствующий Kafka топик.
     * Используется для уведомления других сервисов о изменениях состояния пользователя.
     *
     * @param user      сущность пользователя, с которым связано событие (must not be {@code null})
     * @param eventType тип события для определения топика и содержимого сообщения (must not be {@code null})
     * @throws IllegalArgumentException                 если user или eventType являются {@code null}
     * @throws org.springframework.kafka.KafkaException при ошибках взаимодействия с Kafka
     * @implSpec Реализация должна сериализовать UserEntity в JSON формат для отправки
     * @see EventType
     */
    void sendUserEvent(UserEntity user, EventType eventType);

    /**
     * Отправляет событие создания пользователя для инициации отправки приветственного email.
     * Специализированный метод для обработки сценария регистрации нового пользователя.
     *
     * @param savedUser сущность созданного пользователя (must not be {@code null})
     * @throws IllegalArgumentException                 если savedUser является {@code null}
     * @throws org.springframework.kafka.KafkaException при ошибках взаимодействия с Kafka
     * @implSpec Метод должен отправлять сообщение в топик, предназначенный для email-уведомлений
     * @see #sendUserEvent(UserEntity, EventType)
     */
    void sendEmailOnUserCreate(UserEntity savedUser);

    /**
     * Отправляет событие удаления пользователя для инициации отправки email-уведомления.
     * Специализированный метод для обработки сценария удаления учетной записи.
     *
     * @param deletedUser сущность удаленного пользователя (must not be {@code null})
     * @throws IllegalArgumentException                 если deletedUser является {@code null}
     * @throws org.springframework.kafka.KafkaException при ошибках взаимодействия с Kafka
     * @implSpec Метод должен отправлять сообщение в топик, предназначенный для email-уведомлений
     * @see #sendUserEvent(UserEntity, EventType)
     */
    void sendEmailOnUserDelete(UserEntity deletedUser);
}