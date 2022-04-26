package com.svalero.sportsclubapp.exception;

public class DniAlredyExistException extends Exception{

    //SOBREESCRIBIMOS EL CONSTRUCTOR
    public DniAlredyExistException(String message) {
        super(message);
    }

    //SEGUNDO CONSTRUCTOR PARA PASARLE NOSOTROS EL MENSAJE PERSONALIZADO
    public DniAlredyExistException() {
        super("El DNI introducido ya está en la BBDD, recuerda el DNI debe ser único por Jugador");
    }
}
