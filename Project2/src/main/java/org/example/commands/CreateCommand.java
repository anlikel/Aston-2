package org.example.commands;

import org.example.util.UtilReader;

public class CreateCommand implements Command{
    @Override
    public void execute() {
        UtilReader.writeMessage("create");
    }
}
