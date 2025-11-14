package com.example.notificationhandlers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO (Data Transfer Object), представляющий событие сервиса.
 * Содержит информацию о пользовательских событиях и их метаданные.
 */
public class ServiceEventDto {

    /**
     * Адрес электронной почты, связанный с событием.
     */
    private String email;

    /**
     * Имя, связанное с событием.
     */
    private String name;

    /**
     * Тип события.
     */
    private EventType eventType;

    /**
     * Временная метка, когда произошло событие, в миллисекундах с эпохи Unix.
     */
    private final long timestamp;

    /**
     * Конструктор ServiceEventDto с указанным ID и временной меткой.
     *
     * @param id        уникальный идентификатор события
     * @param timestamp временная метка, когда произошло событие, в миллисекундах с эпохи Unix
     */
    public ServiceEventDto(Long id, long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Конструктор ServiceEventDto с указанной почтой, именем и типом события.
     * Временная метка автоматически устанавливается в текущее системное время.
     *
     * @param email     адрес электронной почты, связанный с событием
     * @param name      имя, связанное с событием
     * @param eventType тип события
     */
    @JsonCreator
    public ServiceEventDto(@JsonProperty("email") String email,
                           @JsonProperty("name") String name,
                           @JsonProperty("eventType") EventType eventType) {
        this.email = email;
        this.name = name;
        this.eventType = eventType;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Возвращает адрес электронной почты, связанный с событием.
     *
     * @return адрес электронной почты
     */
    public String getEmail() {
        return email;
    }

    /**
     * Устанавливает адрес электронной почты, связанный с событием.
     *
     * @param email адрес электронной почты для установки
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Возвращает имя, связанное с событием.
     *
     * @return имя
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя, связанное с событием.
     *
     * @param name имя для установки
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает тип события.
     *
     * @return тип события
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Устанавливает тип события.
     *
     * @param eventType тип события для установки
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Возвращает временную метку, когда произошло событие.
     *
     * @return временная метка в миллисекундах с эпохи Unix
     */
    public long getTimestamp() {
        return timestamp;
    }
}