package org.example.menu;

import org.example.util.UtilReader;

public class MenuPrinter {

    private MenuPrinter(){}

    public static void printMainMenu() {
        UtilReader.writeMessage("1-EXIT");
        UtilReader.writeMessage("2-CREATE USER");
        UtilReader.writeMessage("3-GET USER");
        UtilReader.writeMessage("4-UPDATE USER");
        UtilReader.writeMessage("5-DELETE USER");
        UtilReader.writeMessage("6-GET ALL USERS");
    }
}
