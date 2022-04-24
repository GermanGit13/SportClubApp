package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.Clothing;

import java.sql.Connection;

public class ClothingDao {

    private Connection connection;

    ////MEDIANTE EL CONSTRUCTOR LE PASAMOS LA CONEXIÓN PARA HABLAR CON LA BBDD
    public ClothingDao(Connection connection) {
        this.connection = connection;
    }

    //Añadimos un objeto de la clase Clothing
    public void add(Clothing clothing) {

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
