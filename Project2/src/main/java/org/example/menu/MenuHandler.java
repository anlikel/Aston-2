package org.example.menu;

import org.example.commands.Action;
import org.example.commands.CommandFactory;
import org.example.exceptions.MyCustomException;
import org.example.util.UtilReader;

/**
 * Обработчик главного меню приложения.
 * Обеспечивает выполнение команд в соответствии с выбором пользователя.
 * Реализует паттерн "Команда" через фабрику команд.
 */
public class MenuHandler {

    /**
     * Приватный конструктор для предотвращения создания экземпляров класса.
     * Все методы класса являются статическими.
     */
    private MenuHandler() {
    }

    /**
     * Обрабатывает выбор пользователя в главном меню и выполняет соответствующую команду.
     * Использует фабрику команд для получения и выполнения нужного действия.
     *
     * @param choice строковый идентификатор выбранного пункта меню
     * @throws MyCustomException если выбран неверный или несуществующий пункт меню
     */
    public static void handleMainMenu(String choice) {
        switch (choice) {
            case "1":
                CommandFactory.getCommand(Action.EXIT).execute();
                break;
            case "2":
                CommandFactory.getCommand(Action.CREATE).execute();
                break;
            case "3":
                CommandFactory.getCommand(Action.READ).execute();
                break;
            case "4":
                CommandFactory.getCommand(Action.UPDATE).execute();
                break;
            case "5":
                CommandFactory.getCommand(Action.DELETE).execute();
                break;
            case "6":
                CommandFactory.getCommand(Action.READ_ALL).execute();
                break;
            default:
                throw new MyCustomException("неверный пункт меню");
        }
    }
}