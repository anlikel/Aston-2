package com.example.notificationhandlers;

public class ServiceEventDto {
    private String email;
    private String name;
    private EventType eventType; // CREATE или DELETE

    public ServiceEventDto() {
    }

    public ServiceEventDto(String email, String name, EventType eventType) {
        this.email = email;
        this.name = name;
        this.eventType = eventType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
