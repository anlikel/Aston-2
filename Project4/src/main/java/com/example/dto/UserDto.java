package com.example.dto;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

/**
 * Универсальный DTO для получения/возврата данных
 */
public class UserDto{

    /**
     * Уникальный идентификатор пользователя
     */
    private Long id;

    /**
     * Имя пользователя
     */
    private String name;

    /**
     * Электронная почта пользователя
     */
    private String email;

    /**
     * Возраст пользователя
     */
    private int age;

    /**
     * Дата и время создания пользователя
     */
    private LocalDateTime createdAt;

    private String result;

    /**
     * Конструктор по умолчанию
     * Используется для десериализации JSON
     */
    public UserDto() {
    }

    /**
     * Конструктор со всеми параметрами
     *
     * @param name  имя пользователя
     * @param email электронная почта
     * @param age   возраст пользователя
     */
    public UserDto(String name, String email, int age) {
        this.id = 0L;
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = null;
        this.result = "OK";
    }

    /**
     * Возвращает уникальный идентификатор пользователя
     *
     * @return идентификатор пользователя
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает уникальный идентификатор пользователя
     *
     * @param id идентификатор пользователя
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает имя пользователя
     *
     * @return имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя пользователя
     *
     * @param name имя пользователя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает электронную почту пользователя
     *
     * @return электронная почта
     */
    public String getEmail() {
        return email;
    }

    /**
     * Устанавливает электронную почту пользователя
     *
     * @param email электронная почта
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Возвращает возраст пользователя
     *
     * @return возраст пользователя
     */
    public int getAge() {
        return age;
    }

    /**
     * Устанавливает возраст пользователя
     *
     * @param age возраст пользователя
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Возвращает дату и время создания пользователя
     *
     * @return дата и время создания
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Устанавливает дату и время создания пользователя
     *
     * @param createdAt дата и время создания
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}