package com.svalero.sportsclubapp.exception;

public class TeamNotFoundException extends Exception{

    //SOBREESCRIBIMOS EL CONSTRUCTOR
    public TeamNotFoundException(String message) {
        super (message);
    }

    //SEGUNDO CONSTRUCTOR PARA PASARLE NOSOTROS EL MENSAJE PERSONALIZADO
    public TeamNotFoundException() {
        super("El Equipo no existe");
    }
}
