package com.svalero.sportsclubapp.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * SEGUNDO CONSTRUCTOR PARA PASARLE NOSOTROS EL MENSAJE PERSONALIZADO
     */
    public UserNotFoundException() {
        super("Datos de Usuario no válidos");
    }

}
