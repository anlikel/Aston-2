package com.example.utiltests;

import com.example.util.UtilValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестовый класс для проверки валидации возраста.
 * Содержит тесты для метода {@link UtilValidator#isValidAge(String)}.
 *
 * @author org.example
 * @version 1.0
 */
public class ValidAgeTest {

    /**
     * Тест проверяет обработку отрицательного значения в поле age.
     * Ожидается, что метод вернет false при получении отрицательного числа.
     */
    @Test
    @DisplayName("Тест поля age на отрицательное значение")
    public void isValidAge_WhenAgeIsNegative_ReturnFalse() {
        boolean result = UtilValidator.isValidAge(-1);
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку значения, превышающего верхний предел возраста.
     * Ожидается, что метод вернет false при получении значения 101.
     */
    @Test
    @DisplayName("Тест поля age на превышающее предел значение(ввод 101)")
    public void isValidAge_WhenAgeIsToBig_ReturnFalse() {
        boolean result = UtilValidator.isValidAge(101);
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку валидного значения возраста.
     * Ожидается, что метод вернет true при получении корректного возраста.
     */
    @Test
    @DisplayName("Тест поля age на валидное значение")
    public void isValidAge_WhenAgeIsPositive_ReturnTrue() {
        boolean result = UtilValidator.isValidAge(39);
        assertTrue(result);
    }
}
