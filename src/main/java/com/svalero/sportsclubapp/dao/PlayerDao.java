package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.Player;

import java.sql.Connection;

public class PlayerDao {

    //ATRIBUTO DE TIPO connection
    private Connection connection;

    //MEDIANTE EL CONSTRUCTOR LE PASAMOS EL OBJETO conexion PARA HABLAR CON LA BBDD
    public PlayerDao(Connection connection) {
        this.connection = connection;
    }

    //AÑADIMOS UN OBJETO DE LA CLASE PLAYER
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
