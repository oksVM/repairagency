package com.example.repairagency.exception;


public class NotEnoughMoneyException extends Exception{
    public NotEnoughMoneyException(){
        super("Not enough for money for payment");
    }
}
