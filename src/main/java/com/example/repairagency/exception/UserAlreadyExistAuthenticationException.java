package com.example.repairagency.exception;

import javax.naming.AuthenticationException;

public class UserAlreadyExistAuthenticationException extends AuthenticationException {
    public UserAlreadyExistAuthenticationException() {
        super("User with such login already exist.");
    }

    public UserAlreadyExistAuthenticationException(String email) {
        super(String.format("User with login %s already exist.", email));
    }
}