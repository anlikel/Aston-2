package org.example.commands;

import org.example.util.UtilReader;

public class DeleteCommand implements Command{
    @Override
    public void execute() {
        UtilReader.writeMessage("delete");
    }
}
