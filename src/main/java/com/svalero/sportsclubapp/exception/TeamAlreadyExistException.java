package com.svalero.sportsclubapp.exception;

public class TeamAlreadyExistException extends Exception {

    public TeamAlreadyExistException(String message) {
        super (message);
    }

    /**
     * SEGUNDO CONSTRUCTOR PARA PASARLE NOSOTROS EL MENSAJE PERSONALIZADO
     */
    public TeamAlreadyExistException() {
        super("El Equipo ya existe en esa categoría");
    }
}
