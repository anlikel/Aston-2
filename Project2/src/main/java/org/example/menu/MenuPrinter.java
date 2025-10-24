package org.example.menu;

import org.example.util.UtilReader;

/**
 * Класс для вывода меню приложения в консоль.
 * Обеспечивает отображение доступных опций для взаимодействия с пользователем.
 */
public class MenuPrinter {

    /**
     * Приватный конструктор для предотвращения создания экземпляров класса.
     * Все методы класса являются статическими.
     */
    private MenuPrinter() {
    }

    /**
     * Выводит главное меню приложения с доступными командами.
     * Отображает все основные операции CRUD для работы с пользователями.
     * Использует UtilReader для вывода сообщений в консоль.
     */
    public static void printMainMenu() {
        UtilReader.writeMessage("1-EXIT");
        UtilReader.writeMessage("2-CREATE USER");
        UtilReader.writeMessage("3-GET USER");
        UtilReader.writeMessage("4-UPDATE USER");
        UtilReader.writeMessage("5-DELETE USER");
        UtilReader.writeMessage("6-GET ALL USERS");
    }
}
