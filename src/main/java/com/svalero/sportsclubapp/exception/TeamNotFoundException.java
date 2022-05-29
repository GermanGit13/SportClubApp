package com.svalero.sportsclubapp.exception;

public class TeamNotFoundException extends Exception{

    public TeamNotFoundException(String message) {
        super (message);
    }

    /**
     * SEGUNDO CONSTRUCTOR PARA PASARLE NOSOTROS EL MENSAJE PERSONALIZADO
     */
    public TeamNotFoundException() {
        super("El Equipo no existe");
    }
}
