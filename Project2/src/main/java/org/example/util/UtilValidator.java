package org.example.util;

public class UtilValidator {

    private UtilValidator() {}

    public static boolean isValidName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) { return false; }

        String regex = "^[A-ZА-Я][A-ZА-Яa-zа-я0-9\\-\\s]*$";
        String[] names = fullName.trim().split(" ");

        for (String name : names) {
            if (!name.matches(regex) || name.length()>40) return false;
        }

        return true;
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) { return false; }

        String emailRegex = "^[A-Za-z0-9.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        return email.matches(emailRegex);
    }

    public static boolean isValidAge(String age) {
        if (age == null || age.trim().isEmpty()) { return false; }

        try {
            int ageInt = Integer.parseInt(age.trim());
            return ageInt >= 0 && ageInt <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
