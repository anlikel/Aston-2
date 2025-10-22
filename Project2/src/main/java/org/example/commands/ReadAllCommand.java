package org.example.commands;

import org.example.util.UtilReader;

public class ReadAllCommand implements Command{
    @Override
    public void execute() {
        UtilReader.writeMessage("read all");
    }
}
