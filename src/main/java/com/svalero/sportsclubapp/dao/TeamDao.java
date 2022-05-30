package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.Team;
import com.svalero.sportsclubapp.exception.TeamAlreadyExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;


public class TeamDao {

    private Connection connection;

    /**
     * MEDIANTE EL CONSTRUCTOR LE PASAMOS LA CONEXIÓN PARA HABLAR CON LA BBDD
     */
    public TeamDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * METODO PARA AÑADIR EQUIPOS
     * throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
     * BUSCAMOS PRIMERO SI EL EQUIPO EXISTE USANDO OTRO METODO
     */
    public void add(Team team) throws SQLException, TeamAlreadyExistException {
        if (existTeamAndCategory(team.getName(), team.getCategory()))
            throw new TeamAlreadyExistException();  //AL SER UN OBJETO LA EXCEPCIÓN LA CREAMOS CON NEW

        String sql = "INSERT INTO team (name, category, QUOTA) VALUES (?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, team.getName());
        statement.setString(2, team.getCategory());
        statement.setFloat(3, team.getQuota());
        statement.executeUpdate();
    }

    /**
     * METODO PARA MODIFICAR POR NAME Y CATEGORIA
     * throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
     * BUSCAMOS PRIMERO SI EL EQUIPO EXISTE USANDO OTRO METODO
     * rows PARA DECIRNOS EL NÚMERO DE FILAS QUE HA MODIFICADO
     */
    public boolean modify(String name, String category, Team team) throws SQLException{
        String sql = "UPDATE team SET name = ?, category = ? WHERE name = ? AND category = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, team.getName());
        statement.setString(2, team.getCategory());
        statement.setString(3, name);
        statement.setString(4, category);
        int rows = statement.executeUpdate();
        statement.close();
        return rows ==1;
    }

    /**
     * METODO PARA MODIFICAR POR ID
     */
    public boolean modifyById(int idTeam, Team team) throws SQLException{
        String sql = "UPDATE team SET name = ?, category = ? WHERE id_team = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, team.getName());
        statement.setString(2, String.valueOf(team.getIdTeam()));
        statement.setInt(3, idTeam);
        int rows = statement.executeUpdate();
        statement.close();
        return rows ==1;
    }

    /**
     * METODO PARA MODIFICAR PARA CAMBIAR EL ENTRENADOR POR SU IDUSER SOBRE EL EQUIPO
     */
    public boolean modifyByIdUser(int idUser, Team team) throws SQLException{
        String sql = "UPDATE team SET id_user = ? WHERE id_user = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, team.getIdUser());
        statement.setInt(2, idUser);
        int rows = statement.executeUpdate();
        statement.close();
        return rows ==1;
    }

    /**
     * METODO PARA BORRAR POR NOMBRE Y CATEGORIA
     */
    public boolean delete(String name, String category) throws SQLException {
        String sql = "DELETE FROM team WHERE name = ? AND category = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, category);
        int rows = statement.executeUpdate();
        statement.close();
        return rows ==1;
    }

    /**
     * METODO PARA BORRAR POR ID
     */
    public boolean deleteById(int idTeam) throws SQLException {
        String sql = "DELETE FROM team WHERE id_team = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idTeam);
        int rows = statement.executeUpdate();
        statement.close();
        return rows ==1;
    }

    /**
     * METODO PARA BUSCAR EN TODA LA TABLA TEAM ORDENADO POR CATEGORIA
     * ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
     */
    public ArrayList<Team> findAll() throws SQLException {
        String sql = "SELECT * FROM team ORDER BY category";
        ArrayList<Team> teams = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Team team = fromResultSet(resultSet);
            teams.add(team);
        }
        statement.close();
        return teams;
    }

    /**
     * METODO PARA BUSCAR EN TODA LA TABLA POR UNA CADENA DE TEXTO ORDENADO POR CATEGORIA
     */
    public ArrayList<Team> findAll(String searchText) throws SQLException {
        String sql = "SELECT * FROM team WHERE INSTR(name, ?) !=0 OR INSTR(category, ?) !=0 ORDER BY category";
        ArrayList<Team> teams = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, searchText);
        statement.setString(2, searchText);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Team team = fromResultSet(resultSet);
            teams.add(team);
        }
        statement.close();
        return teams;
    }

    /**
     * METODO PARA BUSCAR POR NOMBRE
     * OPTIONAL SE USA PARA CONTROLAR LA POSIBLE EXCEPCIÓN QUE DEVUELVE UN OBJETO NULO
     */
    public Optional<Team> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM team WHERE name = ?";
        Team team = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            team = fromResultSet(resultSet);
        }
        statement.close();
        return Optional.ofNullable(team);
    }

    /**
     * METODO PARA BUSCAR POR CATEGORIA
     */
    public Team findByCategory(String category) throws SQLException {
        String sql = "SELECT * FROM team WHERE category = ?";
        Team team = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, category);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            team = fromResultSet(resultSet);
        }
        statement.close();
        return team;
    }

    /**
     * METODO PARA BUSCAR POR NOMBRE Y CATEGORIA
     */
    public Team findByTeamAndCategory(String name, String category) throws SQLException {
        String sql ="SELECT * FROM team WHERE name = ? AND category = ?";
        Team team = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, category);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            team = fromResultSet(resultSet);
        }
        statement.close();
        return team;
    }

    /**
     * METODO PARA POR ID
     * OPTIONAL SE USA PARA CONTROLAR LA POSIBLE EXCEPCIÓN QUE DEVUELVE UN OBJETO NULO
     */
    public Optional<Team> findById(int idTeam) throws SQLException {
        String sql = "SELECT * FROM team WHERE id_team = ?";
        Team team = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idTeam);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            team = fromResultSet(resultSet);
        }
        statement.close();
        return Optional.ofNullable(team);
    }

    /**
     * METODO PARA BUSCAR LOS EQUIPOS QUE SON ENTRENADOS POR UN USUARIO POR SU ID_USER
     * OPTIONAL SE USA PARA CONTROLAR LA POSIBLE EXCEPCIÓN QUE DEVUELVE UN OBJETO NULO
     */
    public Optional<Team> findByIdUser(int idUser) throws SQLException {
        String sql = "SELECT * FROM team WHERE id_user = ?";
        Team team = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idUser);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            team = fromResultSet(resultSet);
        }
        statement.close();
        return Optional.ofNullable(team);
    }

    /**
     * METODO PARA BUSCAR SI EXISTE EL EQUIPO Y LA CATEGORIA
     * LO HACEMOS PRIVATE PORQUE SOLO USAREMOS INTERNAMENTE
     */
    private boolean existTeamAndCategory(String name, String category) throws SQLException{
        Team team = findByTeamAndCategory(name, category);
        return team != null;
    }

    /**
     * METODO PARA BUSCAR SI EXISTE EL EQUIPO POR NOMBRE
     * LO HACEMOS PRIVATE PORQUE SOLO USAREMOS INTERNAMENTE
     */
    private boolean existTeam(String name) throws SQLException{
        Optional<Team> team = findByName(name);
        return team.isPresent();
    }

    /**
     * PARA USARLO EN LOS LISTADOS QUE DEVUELVE ResultSet
     */
    private Team fromResultSet(ResultSet resultSet) throws SQLException {
        Team team = new Team();
        team.setName(resultSet.getString("name"));
        team.setCategory(resultSet.getString("category"));
        team.setIdTeam(resultSet.getInt("id_Team"));
        team.setIdUser(resultSet.getString("id_User"));
        team.setQuota(resultSet.getFloat("QUOTA"));
        return team;
    }
}
