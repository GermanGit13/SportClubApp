package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.User;
import com.svalero.sportsclubapp.exception.UserAlredyExistException;
import com.svalero.sportsclubapp.exception.UserExistTablesException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        statement.setString(2, username);
        //PARA DECIRNOS EL NÚMERO DE FILAS QUE HA MODIFICADO
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    public boolean modifyById(int idUser, User user) throws SQLException {
        String sql = "UPDATE users SET firstname = ?, lastname = ?, email = ?, dni = ?, pass = ? WHERE id_user = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getDni());
        statement.setString(5, user.getPass());
        statement.setInt(6, idUser);
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

    public boolean deleteById(int idUser) throws SQLException, UserExistTablesException { //throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
        String sql = "DELETE FROM users WHERE id_user = ?";

        if (existIdUserTeam(idUser))
            throw new UserExistTablesException();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idUser);
        //PARA DECIRNOS EL NÚMERO DE FILAS QUE HA BORRADO
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    //PARA PODER OBTENER UN USUARIO EN CONCRETO
    public Optional<User> login(String username, String password) throws SQLException {
        String sql ="SELECT * FROM users WHERE username = ? AND pass = ?"; //ENCRIPTAR LA PASS CON SHA1(?)
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setIdUser(resultSet.getInt("id_user"));
            user.setFirstName(resultSet.getString("firstname"));
            user.setUsername(resultSet.getString("username"));
            user.setCoach(resultSet.getString("coach")); // USADO COMO ROL PARA ADMINISTRAR LA WEB
        }
        statement.close();
        return Optional.ofNullable(user);
    }

    public ArrayList<User> findAll() throws SQLException { //throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "SELECT * FROM users ORDER BY FirstName";
        ArrayList<User> users = new ArrayList<>();

        //COMPONER EL SQL CON PreparedStatement EN BASE A LA SENTENCIA sql
        PreparedStatement statement = connection.prepareStatement(sql);
        //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
        ResultSet resultSet = statement.executeQuery();
        //RECORREMOS EL resultSet
        while (resultSet.next()) {
            User user = fromResultSet(resultSet);
            users.add(user);
        }
        statement.close();
        return users;
    }

    public ArrayList<User> findAll(String searchText) throws SQLException {
        String sql = "SELECT * FROM users WHERE INSTR(firstName, ?) != 0 OR INSTR(lastname, ?) != 0 ORDER BY firstName";
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, searchText);
        statement.setString(2, searchText);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = fromResultSet(resultSet);
            users.add(user);
        }
        statement.close();
        return users;
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
            user = fromResultSet(resultSet);
        }
        statement.close();
        return user;
    }

    public ArrayList<User> findAllCoach(String searchCoach) throws SQLException {
        String sql = "SELECT * FROM users WHERE coach = ? ORDER BY firstname";
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, searchCoach);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = fromResultSet(resultSet);
            users.add(user);
        }
        statement.close();
        return users;
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
            user = fromResultSet(resultSet);
        }
        statement.close();
        return user;
    }
    public ArrayList<User> findByIdUserTableTeam(int idUser) throws SQLException {
        String sql ="SELECT * FROM team Where id_user = ?";
        ArrayList<User> users = new ArrayList<>();

        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idUser);
        //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            User user = fromResultSet(resultSet);
            users.add(user);
        }
        statement.close();
        return users;
    }
    private boolean existUsername(String username) throws SQLException{
        User user = findByUsername(username);
        return user != null;
    }

    private boolean existIdUserTeam(int idUser) throws SQLException{
        ArrayList<User> users = findByIdUserTableTeam(idUser);
        return users != null;
    }

    public Optional<User> findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id_user = ?";
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = fromResultSet(resultSet);
        }
        statement.close();
        return Optional.ofNullable(user);
    }

    //PARA USARLO EN LOS LISTADO QUE DEVUELVE ResultSet
    private User fromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setIdUser(resultSet.getInt("id_user"));
        user.setFirstName(resultSet.getString("firstname"));
        user.setLastName(resultSet.getString("lastname"));
        user.setEmail(resultSet.getString("email"));
        user.setDni(resultSet.getString("dni"));
        user.setUsername(resultSet.getString("username"));
        user.setPass(resultSet.getString("pass"));
        user.setCoach(resultSet.getString("coach"));
        return user;
    }
}

