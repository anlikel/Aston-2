package com.example.utiltests;

import com.example.util.UtilValidator;
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
    public void isValidId_WhenIdIsNull_ReturnFalse() {
        boolean result = UtilValidator.isValidId(null);
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку отрицательного значения в поле id.
     * Ожидается, что метод вернет false при получении отрицательного числа.
     */
    @Test
    @DisplayName("Тест поля id на отрицательное значение")
    public void isValidId_WhenIdIsNegative_ReturnFalse() {
        boolean result = UtilValidator.isValidId("-1");
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку значения, превышающего максимально допустимое для типа Long.
     * Ожидается, что метод вернет false при получении значения больше Long.MAX_VALUE.
     */
    @Test
    @DisplayName("Тест поля id на превышающее предел значение(Long.MAX_VALUE+1)")
    public void isValidId_WhenIdIsToBig_ReturnFalse() {
        boolean result = UtilValidator.isValidId(String.valueOf(Long.MAX_VALUE + 1));
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку валидного значения id.
     * Ожидается, что метод вернет true при получении корректного положительного числа.
     */
    @Test
    @DisplayName("Тест поля id на валидное значение")
    public void isValidId_WhenIdIsPositive_ReturnTrue() {
        boolean result = UtilValidator.isValidId("39");
        assertTrue(result);
    }

    /**
     * Тест проверяет обработку строки, содержащей специальные символы и буквы.
     * Ожидается, что метод вернет false при получении некорректных символов.
     */
    @Test
    @DisplayName("Тест поля id на ввод символов")
    public void isValidId_WhenIdHaveSpecialSymbols_ReturnFalse() {
        boolean result = UtilValidator.isValidId("qwer123!@#$%");
        assertFalse(result);
    }
}
