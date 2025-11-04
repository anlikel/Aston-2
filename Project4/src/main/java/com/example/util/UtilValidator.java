package com.example.util;

import com.example.entities.UserEntity;

/**
 * Утилитарный класс для валидации пользовательского ввода.
 * Содержит методы для проверки корректности имени, email, возраста и идентификатора.
 */
public class UtilValidator {

    /**
     * Приватный конструктор для предотвращения создания экземпляров класса.
     */
    private UtilValidator() {
    }

    /**
     * Проверяет валидность имени пользователя.
     * Имя должно начинаться с заглавной буквы, может содержать буквы, цифры, дефисы и пробелы,
     * и не должно превышать 40 символов для каждой части имени.
     *
     * @param fullName полное имя для проверки
     * @return true если имя валидно, false в противном случае
     */
    public static boolean isValidName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }

        String regex = "^[A-ZА-Я][A-ZА-Яa-zа-я0-9\\-\\s]*$";
        String[] names = fullName.trim().split(" ");

        for (String name : names) {
            if (!name.matches(regex) || name.length() > 40) return false;
        }

        return true;
    }

    /**
     * Проверяет валидность email адреса.
     * Email должен соответствовать стандартному формату: local-part@domain.tld
     *
     * @param email email адрес для проверки
     * @return true если email валиден, false в противном случае
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        return email.matches(emailRegex);
    }

    /**
     * Проверяет валидность возраста пользователя.
     * Возраст должен быть целым числом в диапазоне от 0 до 100 лет.
     *
     * @param age строка с возрастом для проверки
     * @return true если возраст валиден, false в противном случае
     */
    public static boolean isValidAge(String age) {
        if (age == null || age.trim().isEmpty()) {
            return false;
        }

        try {
            int ageInt = Integer.parseInt(age.trim());
            return ageInt >= 0 && ageInt <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Проверяет валидность идентификатора.
     * Идентификатор должен быть целым числом больше или равным 1.
     *
     * @param id строка с идентификатором для проверки
     * @return true если идентификатор валиден, false в противном случае
     */
    public static boolean isValidId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        try {
            int idInt = Integer.parseInt(id.trim());
            return idInt >= 1 && idInt < Integer.MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}