package org.example.utils;

import org.example.util.UtilValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Тестовый класс для проверки валидации email адресов.
 * Содержит тесты для метода {@link UtilValidator#isValidEmail(String)}.
 *
 * @author org.example
 * @version 1.0
 */
public class ValidEmailTest {

    /**
     * Тест проверяет обработку null значения в поле Email.
     * Ожидается, что метод вернет false при получении null.
     */
    @Test
    @DisplayName("Тест поля Email на ввод Null")
    public void shouldReturnFalse_WhenEmailIsNull() {
        boolean result = UtilValidator.isValidEmail(null);
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку строки, состоящей из одного символа.
     * Ожидается, что метод вернет false при получении слишком короткой строки.
     */
    @Test
    @DisplayName("Тест поля Email на ввод одного символа")
    public void shouldReturnFalse_WhenEmailHaveOneSymbol() {
        boolean result = UtilValidator.isValidEmail("a");
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку пустой строки в поле Email.
     * Ожидается, что метод вернет false при получении пустой строки.
     */
    @Test
    @DisplayName("Тест поля Email на ввод пустой строки")
    public void shouldReturnFalse_WhenEmailIsEmptyString() {
        boolean result = UtilValidator.isValidEmail("");
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку валидного email адреса.
     * Ожидается, что метод вернет true при получении корректного email.
     */
    @Test
    @DisplayName("Тест поля Email на валидное значение")
    public void shouldReturnTrue_WhenEmailIsValid() {
        boolean result = UtilValidator.isValidEmail("test@test.com");
        assertTrue(result);
    }

    /**
     * Тест проверяет обработку невалидного email адреса.
     * Ожидается, что метод вернет false при получении некорректного email.
     */
    @Test
    @DisplayName("Тест поля Email на не валидное значение")
    public void shouldReturnFalse_WhenEmailIsNotValid() {
        boolean result = UtilValidator.isValidEmail("test@@test.com");
        assertFalse(result);
    }
}