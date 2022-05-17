package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.Team;
import com.svalero.sportsclubapp.domain.User;
import com.svalero.sportsclubapp.exception.UserAlredyExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {

    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public void add(User user) throws SQLException, UserAlredyExistException { //throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
        if (existUsername(user.getUsername()))
            throw new UserAlredyExistException();

        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "INSERT INTO users (firstname, lastname, email, dni, username, pass) VALUES (?, ?, ?, ?, ?, ?)";

        //COMPONER EL SQL CON PreparedStatement EN BASE A LA SENTENCIA sql
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());;
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getDni());
        statement.setString(5, user.getUsername());
        statement.setString(6, user.getPass());
        //CUALQUIER CONSULTA QUE NO SEA UN SELECT SE LANZA CON executeUpdate. PARA SELECT USAMOS executeQuery
        statement.executeUpdate();
    }

    //LE PASAMOS QUE USERNAME QUEREMOS MODIFICAR Y EL OBJETO PARA A MODIFICAR
    public boolean modify(String username, User user) throws SQLException{ //throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
        String sql = "UPDATE users SET username = ? WHERE username = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getUsername());
        statement.setString(3, username);
        //PARA DECIRNOS EL NÚMERO DE FILAS QUE HA MODIFICADO
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    public boolean delete(String username, User user) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        //PARA DECIRNOS EL NÚMERO DE FILAS QUE HA BORRADO
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    //PARA PODER OBTENER UN USUARIO EN CONCRETO
    public Optional<User> getUser(String username, String password) throws SQLException {
        String sql ="SELECT * FROM users WHERE username = ? AND password = ?"; //ENCRIPTAR LA PASS CON SHA1(?)
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setIdUser(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("firstname"));
            user.setLastName(resultSet.getString("lastname"));
            user.setEmail(resultSet.getString("Email"));
            user.setDni(resultSet.getString("Dni"));
            user.setUsername(resultSet.getString("username"));
            user.setPass(resultSet.getString("password"));
            user.setCoach(resultSet.getBoolean("coach"));
        }
        return Optional.ofNullable(user);
    }



    public User findByDni(String dni) throws SQLException {
        String sql ="SELECT * FROM users WHERE dni = ?";
        User user = null;

        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dni);
        //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setFirstName(resultSet.getString("FirstName"));
            user.setLastName(resultSet.getString("LastName"));
            user.setDni(resultSet.getString("DNI"));
        }

        return user;
    }

    private boolean existDni(String dni) throws SQLException{
        User user = findByDni(dni);
        return user != null;
    }

    public User findByUsername(String username) throws SQLException {
        String sql ="SELECT * FROM users WHERE username = ?";
        User user = null;

        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setUsername(resultSet.getString("username"));
        }

        return user;
    }

    private boolean existUsername(String username) throws SQLException{
        User user = findByUsername(username);
        return user != null;
    }

    public Optional<User> findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id_user = ?";
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setFirstName(resultSet.getString("FirstName"));
            user.setLastName(resultSet.getString("LastName"));
            user.setEmail(resultSet.getString("Email"));
            user.setDni(resultSet.getString("Dni"));
            user.setUsername(resultSet.getString("Username"));
            user.setIdUser(resultSet.getInt("Id"));
        }
        return Optional.ofNullable(user);
    }

}

