package com.svalero.sportsclubapp.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("Datos de Usuario no válidos");
    }

}
