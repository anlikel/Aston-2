package com.example.serviceinterfaces;

import com.example.entities.UserEntity;
import com.example.notificationhandlers.EventType;

public interface KafkaServiceInterface {
    void sendUserEvent(UserEntity user, EventType eventType);

    void sendEmailOnUserCreate(UserEntity savedUser);

    void sendEmailOnUserDelete(UserEntity deletedUser);
}
