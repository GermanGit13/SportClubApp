package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.Player;
import com.svalero.sportsclubapp.domain.Team;
import com.svalero.sportsclubapp.domain.User;
import com.svalero.sportsclubapp.exception.DniAlredyExistException;
import oracle.jdbc.proxy.annotation.Pre;

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
        String sql = "INSERT INTO player (firstname, lastname, numbers, yearOfBirth, dni, id_user) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player.getFirstName());
        statement.setString(2, player.getLastName());
        statement.setInt(3, player.getNumber());
        statement.setInt(4, player.getYearOfBirth());
        statement.setString(5, player.getDni());
        statement.setInt(6, player.getIdUser());
        //CUALQUIER CONSULTA QUE NO SEA UN SELECT SE LANZA CON executeUpdate. PARA SELECT USAMOS executeQuery
        statement.executeUpdate();
    }

    //AÑADIMOS UN OBJETO DE LA CLASE TEAM
    public void addPlayerTeam(String dni, Player player, Team team)throws SQLException, DniAlredyExistException {
        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "UPDATE player SET firstname = ?, lastname = ?, numbers = ?, yearOfBirth = ?, dni = ?, id_team = ? WHERE DNI = ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player.getFirstName());
        statement.setString(2, player.getLastName());
        statement.setInt(3, player.getNumber());
        statement.setInt(4, player.getYearOfBirth());
        statement.setString(5, player.getDni());
        statement.setInt(6, team.getIdTeam());;
        statement.setString(7, dni);

        //CUALQUIER CONSULTA QUE NO SEA UN SELECT SE LANZA CON executeUpdate. PARA SELECT USAMOS executeQuery
        statement.executeUpdate();
    }

    //LE PASAMOS QUE NOMBRE QUE QUEREMOS MODIFICAR Y EL OBJETO PARA A MODIFICAR
    public boolean modify(String dni, Player player) throws SQLException {
        String sql = "UPDATE player SET firstname = ?, lastname = ?, numbers = ?, yearOfBirth = ?, dni = ? WHERE dni = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player.getFirstName());
        statement.setString(2, player.getLastName());
        statement.setInt(3, player.getNumber());
        statement.setInt(4, player.getYearOfBirth());
        statement.setString(5, player.getDni());
        statement.setString(6, dni);
        //PARA DECIRNOS EL NÚMERO DE FILAS QUE HA MODIFICADO
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    public boolean modifyById(int idPlayer, Player player) throws SQLException {
        String sql = "UPDATE player SET firstname = ?, lastname = ?, numbers = ?, yearOfBirth = ?, dni = ? WHERE id_player = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player.getFirstName());
        statement.setString(2, player.getLastName());
        statement.setInt(3, player.getNumber());
        statement.setInt(4, player.getYearOfBirth());
        statement.setString(5, player.getDni());
        statement.setInt(6, idPlayer);
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
        String sql = "SELECT * FROM player ORDER BY FirstName";
        ArrayList<Player> players = new ArrayList<>();

        //COMPONER EL SQL CON PreparedStatement EN BASE A LA SENTENCIA sql
        PreparedStatement statement = connection.prepareStatement(sql);
        //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
        ResultSet resultSet = statement.executeQuery();
        //RECORREMOS EL resultSet
        while (resultSet.next()) {
            Player player = fromResultSet(resultSet);
            players.add(player);
        }
        statement.close();
        return players;
    }

    //METODO PARA REALIZAR BUSQUEDAS POR CADENAS DE TEXTO EN LAS COLUMNAS QUE QUERAMOS
    public ArrayList<Player> findAll(String searchText) throws SQLException {
        String sql = "SELECT * FROM player WHERE INSTR(firstName, ?) != 0 OR INSTR(lastName, ?) !=0 ORDER BY firstName";
        ArrayList<Player> players = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, searchText);
        statement.setString(2, searchText);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Player player = fromResultSet(resultSet);
            players.add(player);
        }
        statement.close();//PARA CERRAR LA CONEXION CON BBDD
        return players;
    }

    public Optional<Player> findById(int idPlayer) throws SQLException {
        String sql = "SELECT * FROM player WHERE id_player = ?";
        Player player = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idPlayer);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            player = fromResultSet(resultSet);
        }
        return Optional.ofNullable(player);
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
            player = fromResultSet(resultSet);
        }
        statement.close();
        return player;
    }

    private boolean existDni(String dni) throws SQLException{
        Player player = findByDni(dni);
        return player != null;
    }

    //PARA USARLO EN LOS LISTADO QUE DEVUELVE ResultSet
    private Player fromResultSet(ResultSet resultSet) throws SQLException {
        Player player = new Player();

        player.setIdPlayer(resultSet.getInt("id_player"));
        player.setFirstName(resultSet.getString("firstname"));
        player.setLastName(resultSet.getString("lastname"));
        player.setDni(resultSet.getString("dni"));
        player.setYearOfBirth(resultSet.getInt("YearOfBirth"));
        player.setNumber(resultSet.getInt("Numbers"));
        player.setIdTeam(resultSet.getInt("id_team"));
        player.setIdUser(resultSet.getInt("id_user"));
        return player;
    }
}
