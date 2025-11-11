package com.example.notificationhandlers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicLong;

public class ServiceEventDto implements Comparable<ServiceEventDto> {

    private static final AtomicLong idGenerator = new AtomicLong(1);

    private final Long id;
    private String email;
    private String name;
    private EventType eventType; // CREATE или DELETE
    private final long timestamp;

    public ServiceEventDto(Long id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    @JsonCreator
    public ServiceEventDto(@JsonProperty("email") String email,
                           @JsonProperty("name") String name,
                           @JsonProperty("eventType") EventType eventType) {
        this.id = idGenerator.getAndIncrement();
        this.email = email;
        this.name = name;
        this.eventType = eventType;
        this.timestamp = System.currentTimeMillis();
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

    public long getTimestamp() {
        return timestamp;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int compareTo(ServiceEventDto other) {
        int timeCompare = Long.compare(this.timestamp, other.timestamp);
        if (timeCompare != 0) {
            return timeCompare;
        }

        return Long.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "ServiceEventDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", eventType=" + eventType +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceEventDto that = (ServiceEventDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
