package org.example.commands;

import java.util.HashMap;

/**
 * Фабрика для создания команд
 */
public class CommandFactory {
    private static HashMap<Action, Command> map;

    private CommandFactory() {
    }

    /**
     * Возвращает команду по действию
     *
     * @param action действие
     * @return соответствующая команда
     */
    public static Command getCommand(Action action) {
        if (map == null) {
            initActionMap();
        }
        return map.get(action);
    }

    /**
     * Инициализирует карту команд
     */
    private static void initActionMap() {
        map = new HashMap<>();
        map.put(Action.EXIT, new ExitCommand());
        map.put(Action.CREATE, new CreateCommand());
        map.put(Action.READ, new ReadCommand());
        map.put(Action.UPDATE, new UpdateCommand());
        map.put(Action.DELETE, new DeleteCommand());
        map.put(Action.READ_ALL, new ReadAllCommand());
    }
}
