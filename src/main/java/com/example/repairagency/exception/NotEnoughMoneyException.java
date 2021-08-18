package com.example.repairagency.exception;

public class NotEnoughMoneyException extends Exception{
    public NotEnoughMoneyException(String errorMessage){
        super(errorMessage);
    }
}
