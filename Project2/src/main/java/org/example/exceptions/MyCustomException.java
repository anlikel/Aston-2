package org.example.exceptions;

public class MyCustomException extends RuntimeException{
    public MyCustomException(String message){
        super(message);
    }
}
