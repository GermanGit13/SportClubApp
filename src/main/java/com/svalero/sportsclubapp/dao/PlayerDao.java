package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.Player;

import java.sql.Connection;

public class PlayerDao {

    //Atributo de tipo connection
    private Connection connection;

    //Mediante el constructor le pasamos el objeto conexion para hablar con la BBDD
    public PlayerDao(Connection connection) {
        this.connection = connection;
    }

    //Añadimos un objeto de la clase Player
    public void add(Player player) {

    }

    public void modify() {

    }
    public void delete() {

    }

    public void findAll() {

    }

    public void findOne() {

    }
}
