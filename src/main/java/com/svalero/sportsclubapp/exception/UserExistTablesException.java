package com.svalero.sportsclubapp.exception;

public class UserExistTablesException extends Exception{

    //SOBREEESCRIBIMOS EL CONSTRUCTOR
    public UserExistTablesException(String message) {
        super(message);
    }

    //SEGUNDO CONSTRUCTOR PARA PASARLE NOSOTROS EL MENSAJE PERSONALIZADO
    public UserExistTablesException() {
        super("El usuario tiene equipos o jugadores a su cargo, borrelos primero");
    }



}
