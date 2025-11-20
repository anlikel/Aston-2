package com.example.serviceinterfaces;

public interface KafkaListenerEmailNotificationServiceInterface<T> {
    void handleServiceEvent(T eventDto);
}
