package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.User;
import com.svalero.sportsclubapp.exception.UserAlredyExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UserDao {

    private Connection connection;

    /**
     * MEDIANTE EL CONSTRUCTOR LE PASAMOS LA CONEXIÓN PARA HABLAR CON LA BBDD
     */
    public UserDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * METODO PARA AÑADIR USUARIOS
     */
    public void add(User user) throws SQLException, UserAlredyExistException {
        if (existUsername(user.getUsername()))
            throw new UserAlredyExistException();

        String sql = "INSERT INTO users (firstname, lastname, email, dni, username, pass) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());;
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getDni());
        statement.setString(5, user.getUsername());
        statement.setString(6, user.getPass());
        statement.executeUpdate();
    }

    /**
     * METODO PARA MODIFICAR EL NOMBRE DE USUARIO
     */
    public boolean modify(String username, User user) throws SQLException{
        String sql = "UPDATE users SET username = ? WHERE username = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getUsername());
        statement.setString(2, username);
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    /**
     * METODO PARA MODIFICAR POR ID
     */
    public boolean modifyById(int idUser, User user) throws SQLException {
        String sql = "UPDATE users SET firstname = ?, lastname = ?, email = ?, dni = ?, coach = ? WHERE id_user = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getDni());
        statement.setString(5, user.getCoach());
        statement.setInt(6, idUser);
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    /**
     * METODO PARA BORRAR POR NOMBRE DE USUARIO
     */
    public boolean delete(String username, User user) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    /**
     * METODO PARA BORRAR POR ID
     */
    public boolean deleteById(int idUser) throws SQLException {
        String sql = "DELETE FROM users WHERE id_user = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idUser);
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    /**
     * METODO PARA OBTENER UN USUARIO POR SU USERNAME Y PASS
     * ENCRIPTAR LA PASS CON SHA1(?)
     * USAMOS COACH COMO ROL PARA ADMINISTRAR LA WEB
     */
    //PARA PODER OBTENER UN USUARIO EN CONCRETO
    public Optional<User> login(String username, String password) throws SQLException {
        String sql ="SELECT * FROM users WHERE username = ? AND pass = ?";
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
            user.setCoach(resultSet.getString("coach"));
        }
        statement.close();
        return Optional.ofNullable(user);
    }

    /**
     * METODO PARA LISTAR EL CONTENIDO DE LA TABLA USERS ORDENADO POR NOMBRE
     */
    public ArrayList<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY FirstName";
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = fromResultSet(resultSet);
            users.add(user);
        }
        statement.close();
        return users;
    }

    /**
     * METODO PARA BUSCAR EN LA TABLA POR CADENA DE TEXTO EN LAS COLUMNAS QUE DESEEMOS
     */
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

    /**
     * METODO PARA BUSCAR POR DNI
     */
    public User findByDni(String dni) throws SQLException {
        String sql ="SELECT * FROM users WHERE dni = ?";
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dni);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = fromResultSet(resultSet);
        }
        statement.close();
        return user;
    }

    /**
     * METODO PARA BUSCAR LOS USUARIOS QUE SON ENTRENADORES ORDENADOS POR NOMBRE
     */
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

    /**
     * METODO PARA BUSCAR SI EXISTE UN DNI EN LA BBDD
     */
    private boolean existDni(String dni) throws SQLException{
        User user = findByDni(dni);
        return user != null;
    }

    /**
     * METODO PARA BUSCAR POR USERNAME
     */
    public User findByUsername(String username) throws SQLException {
        String sql ="SELECT * FROM users WHERE username = ?";
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = fromResultSet(resultSet);
        }
        statement.close();
        return user;
    }

    /**
     * METODO PARA SI UN USUARIO EXISTE COMO ENTRENADOR DE ALGUN EQUIPO
     */
    public ArrayList<User> findByIdUserTableTeam(int idUser) throws SQLException {
        String sql ="SELECT * FROM team Where id_user = ?";
        ArrayList<User> users = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idUser);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            User user = fromResultSet(resultSet);
            users.add(user);
        }
        statement.close();
        return users;
    }

    /**
     * METODO PARA SI EL NOMBRE DE USUARIO YA EXISTE
     */
    private boolean existUsername(String username) throws SQLException{
        User user = findByUsername(username);
        return user != null;
    }

    /**
     * METODO PARA SI UN USUARIO EXISTE COMO ENTRENADOR DE ALGUN EQUIPO
     */
    private boolean existIdUserTeam(int idUser) throws SQLException{
        ArrayList<User> users = findByIdUserTableTeam(idUser);
        return users != null;
    }

    /**
     * METODO PARA BUSCAR POR ID
     */
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

    /**
     * PARA USARLO EN LOS LISTADOS QUE DEVUELVE ResultSet
     */
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

