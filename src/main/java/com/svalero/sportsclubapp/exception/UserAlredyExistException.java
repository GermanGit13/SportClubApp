package com.svalero.sportsclubapp.exception;

public class UserAlredyExistException extends Exception{

    //SOBREEESCRIBIMOS EL CONSTRUCTOR
    public UserAlredyExistException(String message) {
        super(message);
    }

    //SEGUNDO CONSTRUCTOR PARA PASARLE NOSOTROS EL MENSAJE PERSONALIZADO
    public UserAlredyExistException() {
        super("El usuario ya existe en la BBDD");
    }



}
