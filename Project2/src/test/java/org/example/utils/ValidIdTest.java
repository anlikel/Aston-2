package org.example.utils;

import org.example.util.UtilValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Тестовый класс для проверки валидации идентификаторов (ID).
 * Содержит тесты для метода {@link UtilValidator#isValidAge(String)}.
 *
 * @author org.example
 * @version 1.0
 */
public class ValidIdTest {

    /**
     * Тест проверяет обработку null значения в поле id.
     * Ожидается, что метод вернет false при получении null.
     */
    @Test
    @DisplayName("Тест поля id на null")
    public void shouldReturnFalse_WhenIdIsNull() {
        boolean result = UtilValidator.isValidAge(null);
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку отрицательного значения в поле id.
     * Ожидается, что метод вернет false при получении отрицательного числа.
     */
    @Test
    @DisplayName("Тест поля id на отрицательное значение")
    public void shouldReturnFalse_WhenIdIsNegative() {
        boolean result = UtilValidator.isValidAge("-1");
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку значения, превышающего максимально допустимое для типа Long.
     * Ожидается, что метод вернет false при получении значения больше Long.MAX_VALUE.
     */
    @Test
    @DisplayName("Тест поля id на превышающее предел значение(Long.MAX_VALUE+1)")
    public void shouldReturnFalse_WhenIdIsToBig() {
        boolean result = UtilValidator.isValidAge(String.valueOf(Long.MAX_VALUE + 1));
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку валидного значения id.
     * Ожидается, что метод вернет true при получении корректного положительного числа.
     */
    @Test
    @DisplayName("Тест поля id на валидное значение")
    public void shouldReturnTrue_WhenIdIsPositive() {
        boolean result = UtilValidator.isValidAge("39");
        assertTrue(result);
    }

    /**
     * Тест проверяет обработку строки, содержащей специальные символы и буквы.
     * Ожидается, что метод вернет false при получении некорректных символов.
     */
    @Test
    @DisplayName("Тест поля id на ввод символов")
    public void shouldReturnFalse_WhenIdHaveSpecialSymbols() {
        boolean result = UtilValidator.isValidAge("qwer123!@#$%");
        assertFalse(result);
    }
}