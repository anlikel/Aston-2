package org.example.util;

import org.example.exceptions.MyCustomException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Утилитарный класс для ввода и вывода данных в консоли.
 * Обеспечивает чтение пользовательского ввода с валидацией и вывод сообщений.
 */
public class UtilReader {
    /**
     * Буферизированный читатель для ввода данных из консоли.
     */
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Приватный конструктор для предотвращения создания экземпляров класса.
     */
    private UtilReader() {
    }

    /**
     * Выводит сообщение в консоль.
     *
     * @param message сообщение для вывода
     */
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    /**
     * Читает строку из консоли без валидации.
     *
     * @return введенная пользователем строка
     * @throws MyCustomException если произошла ошибка ввода-вывода
     */
    public static String readMessage() throws MyCustomException {
        String str = null;
        try {
            str = bufferedReader.readLine();
        } catch (IOException e) {
            throw new MyCustomException("исключение: ошибка чтения из консоли");
        }
        return str;
    }

    /**
     * Читает и валидирует имя пользователя.
     *
     * @return валидное имя пользователя
     * @throws MyCustomException если пользователь ввел "exit" или произошла ошибка ввода
     */
    public static String readName() throws MyCustomException {
        UtilReader.writeMessage("input name or type exit");
        while (true) {
            try {
                String name = bufferedReader.readLine();
                if (name.equals("exit")) {
                    throw new MyCustomException("вышли в основное меню");
                }
                if (UtilValidator.isValidName(name)) {
                    return name;
                } else {
                    throw new IOException("некорректное имя");
                }
            } catch (IOException e) {
                UtilReader.writeMessage("исключение: неправильный ввод данных повторите");
                UtilReader.writeMessage("Имя с большой буквы и не больше 40 символов");
            }
        }
    }

    /**
     * Читает и валидирует email пользователя.
     *
     * @return валидный email
     * @throws MyCustomException если пользователь ввел "exit" или произошла ошибка ввода
     */
    public static String readEmail() throws MyCustomException {
        UtilReader.writeMessage("input email or type exit");
        while (true) {
            try {
                String eml = bufferedReader.readLine();
                if (eml.equals("exit")) {
                    throw new MyCustomException("вышли в основное меню");
                }
                if (UtilValidator.isValidEmail(eml)) {
                    return eml;
                } else {
                    throw new IOException("некорректная почта");
                }
            } catch (IOException e) {
                UtilReader.writeMessage("исключение: неправильный ввод данных повторите");
            }
        }
    }

    /**
     * Читает и валидирует возраст пользователя.
     *
     * @return валидный возраст
     * @throws MyCustomException если пользователь ввел "exit" или произошла ошибка ввода
     */
    public static int readAge() throws MyCustomException {
        UtilReader.writeMessage("input age or type exit");
        while (true) {
            try {
                String age = bufferedReader.readLine();
                if (age.equals("exit")) {
                    throw new MyCustomException("вышли в основное меню");
                }
                if (UtilValidator.isValidAge(age)) {
                    return Integer.valueOf(age);
                } else {
                    throw new IOException("некорректный возраст");
                }
            } catch (IOException e) {
                UtilReader.writeMessage("исключение: неправильный ввод данных повторите");
                UtilReader.writeMessage("возраст от 0 до 100");
            }
        }
    }

    /**
     * Читает и валидирует идентификатор пользователя.
     *
     * @return валидный идентификатор
     * @throws MyCustomException если пользователь ввел "exit" или произошла ошибка ввода
     */
    public static Long readId() throws MyCustomException {
        UtilReader.writeMessage("input id or type exit");
        while (true) {
            try {
                String id = bufferedReader.readLine();
                if (id.equals("exit")) {
                    throw new MyCustomException("вышли в основное меню");
                } else if (UtilValidator.isValidId(id)) {
                    return Long.valueOf(id);
                } else {
                    throw new IOException("некорректное число");
                }
            } catch (IOException e) {
                UtilReader.writeMessage("исключение: неправильный ввод данных повторите");
                UtilReader.writeMessage("id >= 1");
            }
        }
    }
}