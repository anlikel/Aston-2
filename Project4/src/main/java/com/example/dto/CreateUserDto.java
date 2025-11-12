package com.example.dto;

/**
 * DTO для создания нового пользователя
 * Используется для передачи данных в Post и Put запросах
 */
public class CreateUserDto {

    /**
     * Имя пользователя
     * Не может быть пустым или null
     */
    private String name;

    /**
     * Электронная почта пользователя
     * Должна быть валидным email адресом
     */
    private String email;

    /**
     * Возраст пользователя
     * Должен быть положительным числом
     */
    private Integer age;

    /**
     * Конструктор по умолчанию
     * Используется для десериализации JSON
     */
    public CreateUserDto() {
    }

    /**
     * Конструктор со всеми параметрами
     *
     * @param name  имя пользователя
     * @param email электронная почта
     * @param age   возраст пользователя
     */
    public CreateUserDto(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
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
    public Integer getAge() {
        return age;
    }

    /**
     * Устанавливает возраст пользователя
     *
     * @param age возраст пользователя
     */
    public void setAge(Integer age) {
        this.age = age;
    }
}