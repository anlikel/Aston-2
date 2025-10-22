package org.example.menu;

import org.example.commands.Action;
import org.example.commands.CommandFactory;
import org.example.exceptions.MyCustomException;
import org.example.util.UtilReader;

public class MenuHandler {

    private MenuHandler(){}

    public static void handleMainMenu(String choice) {
        switch (choice) {
            case "1":
                CommandFactory.getCommand(Action.EXIT).execute();
                break;
            case "2":
                CommandFactory.getCommand(Action.CREATE).execute();
                break;
            case "3":
                CommandFactory.getCommand(Action.READ).execute();
                break;
            case "4":
                CommandFactory.getCommand(Action.UPDATE).execute();
                break;
            case "5":
                CommandFactory.getCommand(Action.DELETE).execute();
                break;
            case "6":
                CommandFactory.getCommand(Action.READ_ALL).execute();
                break;
            default:
                throw new MyCustomException("неверный пункт меню");
        }
    }
}
