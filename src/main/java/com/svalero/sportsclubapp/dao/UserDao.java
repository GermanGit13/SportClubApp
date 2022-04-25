package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.User;
import com.svalero.sportsclubapp.exception.TeamAlreadyExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    //TODO preparar este DAO con las operaciones básicas (CRUD: Create Read Update Delete)

    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    //AÑADIMOS UN OBJETO DE LA CLASE TEAM
    //TODO CREAR EXCEPCIÓN SI EL USUARIO YA EXISTE
    public void add(User user) throws SQLException { //throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
        //BUSCAMOS PRIMERO SI EL EQUIPO EXISTE, SE PUEDE USAR PARA BUSCAR EL DNI

        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "INSERT INTO user (firstname, lastname, email, dni, username, password, coach) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        //COMPONER EL SQL CON PreparedStatement EN BASE A LA SENTENCIA sql
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getDni());
        statement.setString(5, user.getUsername());
        statement.setString(6, user.getPassword());
        statement.setBoolean(7, user.isCoach());
        //CUALQUIER CONSULTA QUE NO SEA UN SELECT SE LANZA CON executeUpdate. PARA SELECT USAMOS executeQuery
        statement.executeUpdate();
    }

    //TODO TERMINAR RESTO DE MÉTODOS: modifyUser, deleteUser, getters and setters
}

