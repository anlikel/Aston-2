package org.example.commands;

/** Контракт для выполнения команд */
public interface Command {
    /** Запускает выполнение команды */
    void execute();
}