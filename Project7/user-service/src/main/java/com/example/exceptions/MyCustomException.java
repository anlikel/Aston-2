package com.example.exceptions;

/**
 * Пользовательское непроверяемое исключение.
 * Используется для обработки специфичных ошибок бизнес-логики приложения.
 * Наследуется от RuntimeException, что позволяет не объявлять его в сигнатурах методов.
 */
public class MyCustomException extends RuntimeException {

    /**
     * Создает новое исключение с указанным сообщением об ошибке.
     *
     * @param message детальное сообщение об ошибке, описывающее причину исключения
     */
    public MyCustomException(String message) {
        super(message);
    }
}
