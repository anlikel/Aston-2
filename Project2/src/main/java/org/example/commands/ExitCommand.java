package org.example.commands;

import org.example.util.UtilReader;

public class ExitCommand implements Command{
    @Override
    public void execute() {
        UtilReader.writeMessage("exit");
        System.exit(0);
    }
}
