package com.example.repairagency.exception;

public class OrderNotFoundException extends Exception{

    public OrderNotFoundException() {
        super("Order doesn't exist.");
    }

    public OrderNotFoundException(Long id) {
        super(String.format("Order with id %d doesn't exist.", id));
    }

}
