package org.example;

import org.example.exceptions.MyCustomException;
import org.example.menu.MenuHandler;
import org.example.menu.MenuPrinter;
import org.example.util.HibernateUtil;
import org.example.util.UtilReader;

/**
 * Главный класс приложения для управления пользователями.
 * Содержит точку входа в программу и основной цикл работы.
 * Координирует взаимодействие между компонентами приложения и управляет жизненным циклом.
 */
public class Main {

    /**
     * Точка входа в приложение.
     * Запускает основной цикл работы программы с обработкой исключений.
     * Инициализирует и сбрасывает состояние HibernateUtil перед началом работы.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        resetHibernateUtil();
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
     * Обеспечивает непрерывную работу приложения до явного завершения.
     *
     * @throws MyCustomException если произошла ошибка при обработке выбора пользователя
     */
    static void mainCycle() {
        MenuPrinter.printMainMenu();
        String choose = UtilReader.readMessage();
        MenuHandler.handleMainMenu(choose);
    }

    /**
     * Сбрасывает состояние HibernateUtil для корректной работы с сессиями Hibernate.
     * Закрывает существующую фабрику сессий, очищает тестовую фабрику сессий
     * и обеспечивает паузу для завершения операций очистки.
     * Обрабатывает возможные исключения при сбросе состояния.
     */
    private static void resetHibernateUtil() {
        try {
            HibernateUtil.closeSessionFactory();
            HibernateUtil.clearTestSessionFactory();

            Thread.sleep(500);

            System.out.println("HibernateUtil reset completed");

        } catch (Exception e) {
            System.err.println("Warning during HibernateUtil reset: " + e.getMessage());
        }
    }
}