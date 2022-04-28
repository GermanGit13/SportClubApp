package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.Player;
import com.svalero.sportsclubapp.domain.Team;
import com.svalero.sportsclubapp.exception.DniAlredyExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class PlayerDao {

    //ATRIBUTO DE TIPO connection
    private Connection connection;

    //MEDIANTE EL CONSTRUCTOR LE PASAMOS LA CONEXIÓN PARA HABLAR CON LA BBDD
    public PlayerDao(Connection connection) {
        this.connection = connection;
    }

    //AÑADIMOS UN OBJETO DE LA CLASE PLAYER
    public void add(Player player)throws SQLException, DniAlredyExistException {
        //BUSCAMOS PRIMERO SI EL EQUIPO EXISTE, SE PUEDE USAR PARA BUSCAR EL DNI
        if (existDni(player.getDni()))
            throw new DniAlredyExistException();

        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "INSERT INTO player (firstname, lastname, number, yearOfBirth, dni) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player.getFirstName());
        statement.setString(2, player.getLastName());
        statement.setInt(3, player.getNumber());
        statement.setInt(4, player.getYearOfBirth());
        statement.setString(5, player.getDni());
        //CUALQUIER CONSULTA QUE NO SEA UN SELECT SE LANZA CON executeUpdate. PARA SELECT USAMOS executeQuery
        statement.executeUpdate();
    }

    //AÑADIMOS UN OBJETO DE LA CLASE PLAYER
    public void addPlayerTeam(Player player, Team team)throws SQLException, DniAlredyExistException {
        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "INSERT INTO player (firstname, lastname, number, yearOfBirth, dni) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player.getFirstName());
        statement.setString(2, player.getLastName());
        statement.setInt(3, player.getNumber());
        statement.setInt(4, player.getYearOfBirth());
        statement.setString(5, player.getDni());
        //CUALQUIER CONSULTA QUE NO SEA UN SELECT SE LANZA CON executeUpdate. PARA SELECT USAMOS executeQuery
        statement.executeUpdate();
    }

    //LE PASAMOS QUE NOMBRE QUE QUEREMOS MODIFICAR Y EL OBJETO PARA A MODIFICAR
    public boolean modify(String dni, Player player) throws SQLException {
        String sql = "UPDATE player SET firstname = ?, lastname = ?, number = ?, yearOfBirth = ?, dni = ? WHERE DNI = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player.getFirstName());
        statement.setString(2, player.getLastName());
        statement.setInt(3, player.getNumber());
        statement.setInt(4, player.getYearOfBirth());
        statement.setString(5, player.getDni());
        statement.setString(5, dni);
        //PARA DECIRNOS EL NÚMERO DE FILAS QUE HA MODIFICADO
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    public boolean delete(String dni, Player player) throws SQLException {
        String sql = "DELETE FROM player WHERE dni = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dni);
        //PARA DECIRNOS EL NÚMERO DE FILAS QUE HA BORRADO
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    public ArrayList<Player> findAll() throws SQLException {
        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "SELECT * FROM player ORDEN BY name";
        ArrayList<Player> players = new ArrayList<>();

        //COMPONER EL SQL CON PreparedStatement EN BASE A LA SENTENCIA sql
        PreparedStatement statement = connection.prepareStatement(sql);
        //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
        ResultSet resultSet = statement.executeQuery();
        //RECORREMOS EL resultSet
        while (resultSet.next()) {
            Player player = new Player();
            player.setFirstName(resultSet.getString("Name"));
            player.setLastName(resultSet.getString("Apellidos"));
            player.setNumber(resultSet.getInt("Dorsal"));
            player.setYearOfBirth(resultSet.getInt("Fecha nacimiento"));
            player.setDni(resultSet.getString("Dni"));
            players.add(player);
        }
        return players;
    }

    public Player findByDni(String dni) throws SQLException {
        String sql ="SELECT * FROM player WHERE dni = ?";
        Player player = null;

        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dni);
        //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            player = new Player();
            player.setFirstName(resultSet.getString("FirstName"));
            player.setLastName(resultSet.getString("LastName"));
            player.setDni(resultSet.getString("DNI"));
            //TODO ver como devolver el objeto equipo
        }

        return player;
    }

    private boolean existDni(String dni) throws SQLException{
        Player player = findByDni(dni);
        return player != null;
    }
}
