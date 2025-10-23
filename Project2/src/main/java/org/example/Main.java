package org.example;

import org.example.exceptions.MyCustomException;
import org.example.menu.MenuHandler;
import org.example.menu.MenuPrinter;
import org.example.util.UtilReader;

public class Main {
    public static void main(String [] args){

        while (true) {
            try {
                mainCycle();
            } catch (MyCustomException e) {
                UtilReader.writeMessage(e.getMessage());
            }
        }
    }

    static void mainCycle(){
        MenuPrinter.printMainMenu();
        String choose = UtilReader.readMessage();
        MenuHandler.handleMainMenu(choose);
    }
}