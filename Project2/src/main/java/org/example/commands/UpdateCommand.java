package org.example.commands;

import org.example.util.UtilReader;

public class UpdateCommand implements Command{
    @Override
    public void execute() {
        UtilReader.writeMessage("update");
    }
}
