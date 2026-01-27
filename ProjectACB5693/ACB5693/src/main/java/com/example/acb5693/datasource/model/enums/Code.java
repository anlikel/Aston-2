package com.example.acb5693.datasource.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Code {
    CREATE("Создание"),
    READ("Чтение"),
    UPDATE("Обновление"),
    DELETE("Удаление");

    private final String description;
}

