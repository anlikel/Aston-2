package org.example.util;

import org.example.exceptions.MyCustomException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UtilReader {
    private static BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));

    private UtilReader(){}

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readMessage() throws MyCustomException {
        String str=null;
        try{
            str=bufferedReader.readLine();
        } catch (IOException e) {
            throw new MyCustomException("исключение: ошибка чтения из консоли");
        }
        return str;
    }

    public static String readName() throws MyCustomException{
        while (true) {
            try {
                String name = bufferedReader.readLine();
                if(name.equals("exit")){throw new MyCustomException("вышли в основное меню");}
                if (UtilValidator.isValidName(name)) {
                    return name;
                } else {
                    throw new IOException("некорректное имя");
                }
            } catch (IOException e) {
                UtilReader.writeMessage("исключение: неправильный ввод данных повторите");
                UtilReader.writeMessage("Имя с большой буквы и не больше 40 символов");
            }
        }
    }

    public static String readEmail() throws MyCustomException{
        while (true) {
            try {
                String eml = bufferedReader.readLine();
                if(eml.equals("exit")){throw new MyCustomException("вышли в основное меню");}
                if (UtilValidator.isValidEmail(eml)) {
                    return eml;
                } else {
                    throw new IOException("некорректная почта");
                }
            } catch (IOException e) {
                UtilReader.writeMessage("исключение: неправильный ввод данных повторите");
            }
        }
    }

    public static int readAge() throws MyCustomException{
        while (true) {
            try {
                String age = bufferedReader.readLine();
                if(age.equals("exit")){throw new MyCustomException("вышли в основное меню");}
                if (UtilValidator.isValidAge(age)) {
                    return Integer.valueOf(age);
                } else {
                    throw new IOException("некорректный возраст");
                }
            } catch (IOException e) {
                UtilReader.writeMessage("исключение: неправильный ввод данных повторите");
                UtilReader.writeMessage("возраст от 0 до 100");
            }
        }
    }

    public static int readId() throws MyCustomException{
        while (true) {
            try {
                String id = bufferedReader.readLine();
                if(id.equals("exit")){throw new MyCustomException("вышли в основное меню");}
                if (UtilValidator.isValidId(id)) {
                    return Integer.valueOf(id);
                } else {
                    throw new IOException("некорректное число");
                }
            } catch (IOException e) {
                UtilReader.writeMessage("исключение: неправильный ввод данных повторите");
                UtilReader.writeMessage("id >= 1");
            }
        }
    }

}
