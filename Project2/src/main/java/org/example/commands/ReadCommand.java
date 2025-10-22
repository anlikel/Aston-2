package org.example.commands;

import org.example.util.UtilReader;

public class ReadCommand implements Command{
    @Override
    public void execute() {
        UtilReader.writeMessage("read");
    }
}
