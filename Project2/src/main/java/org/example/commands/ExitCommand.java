package org.example.commands;

import org.example.util.UtilReader;

/**
 * Команда выхода из приложения
 */
public class ExitCommand implements Command {
    /**
     * Завершает работу приложения
     */
    @Override
    public void execute() {
        UtilReader.writeMessage("exit");
        System.exit(0);
    }
}
