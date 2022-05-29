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

    private Connection connection;

    /**
     * CONSTRUCTOR PARA CONECTAR CON LA bbdd
     */
    public PlayerDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * BUSCAMOS PRIMERO SI EL EQUIPO EXISTE, SE PUEDE USAR PARA BUSCAR EL DNI
     */
    public void add(Player player)throws SQLException, DniAlredyExistException {
        if (existDni(player.getDni()))
            throw new DniAlredyExistException();

        String sql = "INSERT INTO player (firstname, lastname, numbers, yearOfBirth, dni, id_user) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player.getFirstName());
        statement.setString(2, player.getLastName());
        statement.setInt(3, player.getNumber());
        statement.setInt(4, player.getYearOfBirth());
        statement.setString(5, player.getDni());
        statement.setInt(6, player.getIdUser());
        statement.executeUpdate();
    }

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

        statement.executeUpdate();
    }

    /**
     * PARA MODIFICAR POR DNI
     * rows PARA DECIRNOS EL NÚMERO DE FILAS QUE HA MODIFICADO
     */
    public boolean modify(String dni, Player player) throws SQLException {
        String sql = "UPDATE player SET firstname = ?, lastname = ?, numbers = ?, yearOfBirth = ?, dni = ? WHERE dni = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player.getFirstName());
        statement.setString(2, player.getLastName());
        statement.setInt(3, player.getNumber());
        statement.setInt(4, player.getYearOfBirth());
        statement.setString(5, player.getDni());
        statement.setString(6, dni);

        int rows = statement.executeUpdate();
        return rows ==1;
    }

    /**
     * PARA MODIFICAR POR ID
     * rows PARA DECIRNOS EL NÚMERO DE FILAS QUE HA MODIFICADO
     */
    public boolean modifyById(int idPlayer, Player player) throws SQLException {
        String sql = "UPDATE player SET firstname = ?, lastname = ?, numbers = ?, yearOfBirth = ?, dni = ? WHERE id_player = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player.getFirstName());
        statement.setString(2, player.getLastName());
        statement.setInt(3, player.getNumber());
        statement.setInt(4, player.getYearOfBirth());
        statement.setString(5, player.getDni());
        statement.setInt(6, idPlayer);

        int rows = statement.executeUpdate();
        return rows ==1;
    }

    /**
     * PARA BORRAR POR DNI
     * rows PARA DECIRNOS EL NÚMERO DE FILAS QUE HA MODIFICADO
     */
    public boolean delete(String dni, Player player) throws SQLException {
        String sql = "DELETE FROM player WHERE dni = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dni);

        int rows = statement.executeUpdate();
        return rows ==1;
    }

    /**
     * METODO PARA BORRAR POR ID
     */
    public boolean deleteById(int idPlayer) throws SQLException {
        String sql = "DELETE FROM player WHERE id_player = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idPlayer);
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    /**
     * LISTADO DE LA TABLA PLAYER
     * ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
     */
    public ArrayList<Player> findAll() throws SQLException {
        String sql = "SELECT * FROM player ORDER BY FirstName";
        ArrayList<Player> players = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Player player = fromResultSet(resultSet);
            players.add(player);
        }
        statement.close();
        return players;
    }

    /**
     * METODO PARA REALIZAR BUSQUEDAS POR CADENAS DE TEXTO EN LAS COLUMNAS QUE QUERAMOS
     * ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
     */
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
        statement.close();
        return players;
    }

    /**
     * METODO PARA BUSCAR POR ID
     */
    public Optional<Player> findById(int idPlayer) throws SQLException {
        String sql = "SELECT * FROM player WHERE id_player = ?";
        Player player = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idPlayer);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            player = fromResultSet(resultSet);
        }
        statement.close();
        return Optional.ofNullable(player);
    }

    /**
     * METODO PARA BUSCAR LOS JUGADORES QUUE DEPENDE DE UN USUARIO POR SU ID
     */
    public ArrayList<Player> findByIdUSer(int idUser) throws SQLException {
        String sql = "SELECT * FROM player WHERE id_user = ? ORDER BY firstName";
        ArrayList<Player> players = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idUser);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Player player = fromResultSet(resultSet);
            players.add(player);
        }
        statement.close();
        return players;
    }

    /**
     * METODO PARA BUSCAR POR DNI
     */
    public Player findByDni(String dni) throws SQLException {
        String sql ="SELECT * FROM player WHERE dni = ?";
        Player player = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dni);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            player = fromResultSet(resultSet);
        }
        statement.close();
        return player;
    }

    /**
     * METODO PARA BUSCAR SI EXISTE EL DNI EN LA BBDD
     */
    private boolean existDni(String dni) throws SQLException{
        Player player = findByDni(dni);
        return player != null;
    }

    /**
     * METODO CONTAR EN NÚMERO DE JUGADORES EN UN EQUIPO POR SU IDTEAM
     */
    public int countByTeam(int idTeam) throws SQLException {
        String sql ="SELECT count(*) FROM player WHERE idTeam = ?";
        Player player = null;
        int countPlayer = 0;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idTeam);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            countPlayer = resultSet.getInt(1);
        }
        statement.close();
        return countPlayer;
    }

    /**
     * PARA USARLO EN LOS LISTADOS QUE DEVUELVE ResultSet
     */
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
