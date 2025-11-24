package com.example.utiltests;

import com.example.util.UtilValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Тестовый класс для проверки валидации имен.
 * Содержит тесты для метода {@link UtilValidator#isValidName(String)}.
 *
 * @author org.example
 * @version 1.0
 */
public class ValidNameTest {

    /**
     * Тест проверяет обработку null значения в поле name.
     * Ожидается, что метод вернет false при получении null.
     */
    @Test
    @DisplayName("Тест поля name на ввод null")
    void isValidName_WhenNameIsNull_ReturnFalse() {
        boolean result = UtilValidator.isValidName(null);
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку валидного имени, состоящего из одного символа.
     * Ожидается, что метод вернет true при получении корректного короткого имени.
     */
    @Test
    @DisplayName("Тест поля name на ввод одного символа")
    void isValidName_WhenNameHaveOneSymbol_ReturnTrue() {
        boolean result = UtilValidator.isValidName("A");
        assertTrue(result);
    }

    /**
     * Тест проверяет обработку имени, превышающего максимальную допустимую длину (41 символ).
     * Ожидается, что метод вернет false при получении слишком длинного имени.
     */
    @Test
    @DisplayName("Тест поля name на ввод 41 символов")
    void isValidName_WhenNameIsToLong_ReturnFalse() {
        boolean result = UtilValidator.isValidName("Aaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertFalse(result);
    }

    /**
     * Тест проверяет обработку имени, содержащего недопустимые специальные символы.
     * Ожидается, что метод вернет false при получении имени с запрещенными символами.
     */
    @Test
    @DisplayName("Тест поля name на содержание недопустимых символов")
    void isValidName_WhenNameIsHaveSpecialSymbols_ReturnFalse() {
        boolean result = UtilValidator.isValidName("Qw!%:?;{}");
        assertFalse(result);
    }
}
