package org.example;

import org.example.exceptions.MyCustomException;
import org.example.menu.MenuHandler;
import org.example.menu.MenuPrinter;
import org.example.util.UtilReader;

/**
 * Главный класс приложения для управления пользователями.
 * Содержит точку входа в программу и основной цикл работы.
 */
public class Main {

    /**
     * Точка входа в приложение.
     * Запускает основной цикл работы программы с обработкой исключений.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        while (true) {
            try {
                mainCycle();
            } catch (MyCustomException e) {
                UtilReader.writeMessage(e.getMessage());
            }
        }
    }

    /**
     * Основной цикл работы приложения.
     * Отображает главное меню, обрабатывает выбор пользователя и выполняет соответствующие действия.
     *
     * @throws MyCustomException если произошла ошибка при обработке выбора пользователя
     */
    static void mainCycle() {
        MenuPrinter.printMainMenu();
        String choose = UtilReader.readMessage();
        MenuHandler.handleMainMenu(choose);
    }
}