package org.example.utils;

import org.example.util.UtilValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Тестовый класс для проверки валидации возраста.
 * Содержит тесты для метода {@link UtilValidator#isValidAge(String)}.
 *
 * @author org.example
 * @version 1.0
 */
public class ValidAgeTest {

    /**
     * Тест проверяет обработку null значения в поле age.
     * Ожидается, что метод вернет false при получении null.
     */
    @Test
    @DisplayName("Тест поля age на null")
    public void shouldReturnFalse_WhenAgeIsNull() {
        boolean result = UtilValidator.isValidAge(null);
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку отрицательного значения в поле age.
     * Ожидается, что метод вернет false при получении отрицательного числа.
     */
    @Test
    @DisplayName("Тест поля age на отрицательное значение")
    public void shouldReturnFalse_WhenAgeIsNegative() {
        boolean result = UtilValidator.isValidAge("-1");
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку значения, превышающего верхний предел возраста.
     * Ожидается, что метод вернет false при получении значения 101.
     */
    @Test
    @DisplayName("Тест поля age на превышающее предел значение(ввод 101)")
    public void shouldReturnFalse_WhenAgeIsToBig() {
        boolean result = UtilValidator.isValidAge("101");
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку валидного значения возраста.
     * Ожидается, что метод вернет true при получении корректного возраста.
     */
    @Test
    @DisplayName("Тест поля age на валидное значение")
    public void shouldReturnTrue_WhenAgeIsPositive() {
        boolean result = UtilValidator.isValidAge("39");
        assertTrue(result);
    }

    /**
     * Тест проверяет обработку строки, содержащей специальные символы и буквы.
     * Ожидается, что метод вернет false при получении некорректных символов.
     */
    @Test
    @DisplayName("Тест поля age на ввод символов")
    public void shouldReturnFalse_WhenAgeHaveSpecialSymbols() {
        boolean result = UtilValidator.isValidAge("qwer123!@#$%");
        assertFalse(result);
    }
}