package com.svalero.sportsclubapp.dao;

import com.mysql.cj.util.DnsSrv;
import com.svalero.sportsclubapp.domain.Team;
import com.svalero.sportsclubapp.exception.TeamAlreadyExistException;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
//import java.util.Optional;

public class TeamDao {

    private Connection connection;

    //MEDIANTE EL CONSTRUCTOR LE PASAMOS LA CONEXIÓN PARA HABLAR CON LA BBDD
    public TeamDao(Connection connection) {
        this.connection = connection;
    }

    //AÑADIMOS UN OBJETO DE LA CLASE TEAM
    public void add(Team team) throws SQLException, TeamAlreadyExistException { //throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
        //BUSCAMOS PRIMERO SI EL EQUIPO EXISTE, SE PUEDE USAR PARA BUSCAR EL DNI
        if (existTeamAndCategory(team.getName(), team.getCategory()))
            throw new TeamAlreadyExistException();  //AL SER UN OBJETO LA EXCEPCIÓN LA CREAMOS CON NEW

        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "INSERT INTO team (name, category, QUOTA) VALUES (?, ?, ?)";

        //COMPONER EL SQL CON PreparedStatement EN BASE A LA SENTENCIA sql
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, team.getName());
        statement.setString(2, team.getCategory());
        statement.setFloat(3, team.getQuota());
        //CUALQUIER CONSULTA QUE NO SEA UN SELECT SE LANZA CON executeUpdate. PARA SELECT USAMOS executeQuery
        statement.executeUpdate();
    }

    //LE PASAMOS QUE NOMBRE QUE QUEREMOS MODIFICAR Y EL OBJETO PARA A MODIFICAR
    public boolean modify(String name, Team team) throws SQLException{ //throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
        String sql = "UPDATE TEAM SET NAME = ?, CATEGORY = ? WHERE NAME = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, team.getName());
        statement.setString(2, team.getCategory());
        statement.setString(3, name);
        //PARA DECIRNOS EL NÚMERO DE FILAS QUE HA BORRADO
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    public boolean delete(String name) throws SQLException { //throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
        String sql = "DELETE FROM TEAM WHERE name = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        //PARA DECIRNOS EL NÚMERO DE FILAS QUE HA BORRADO
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    public ArrayList<Team> findAll() throws SQLException { //throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "SELECT * FROM TEAM ORDEN BY name";
        ArrayList<Team> teams = new ArrayList<>();

        //COMPONER EL SQL CON PreparedStatement EN BASE A LA SENTENCIA sql
        PreparedStatement statement = connection.prepareStatement(sql);
        //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
        ResultSet resultSet = statement.executeQuery();
        //RECORREMOS EL resultSet
        while (resultSet.next()) {
            Team team = new Team();
            team.setName(resultSet.getString("Name"));
            team.setCategory(resultSet.getString("Category"));
            //TODO REVISAR COMO IMPRIMIR LA QUOTA
            teams.add(team);
        }
        return teams;
    }

    //OPTIONAL SE USA PARA CONTROLAR LA POSIBLE EXCEPCIÓN QUE DEVUELVE UN OBJETO NULO
    public Optional<Team> findByName(String name) throws SQLException { //throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "SELECT * FROM TEAM WHERE name = ?";
        Team team = null;

        //COMPONER EL SQL CON PreparedStatement EN BASE A LA SENTENCIA sql
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            team = new Team();
            team.setName(resultSet.getString("Name"));
            team.setCategory(resultSet.getString("Category"));
            //TODO REVISAR COMO IMPRIMIR LA QUOTA
        }

        return Optional.ofNullable(team);
    }

    public Team findByCategory(String category) throws SQLException { //throws PARA PROPAGAR LA EXCEPCIÓN HACIA UNA CAPA SUPERIOR
        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "SELECT * FROM TEAM WHERE category = ?";
        Team team = null;

        //COMPONER EL SQL CON PreparedStatement EN BASE A LA SENTENCIA sql
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, category);
        //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            team = new Team();
            team.setName(resultSet.getString("Name"));
            team.setCategory(resultSet.getString("Category"));
            //TODO REVISAR COMO IMPRIMIR LA QUOTA
        }

        return team;
    }

    public Team findByTeamAndCategory(String name, String category) throws SQLException {
        String sql ="SELECT * FROM TEAM WHERE name = ? AND CATEGORY = ?";
        Team team = null;

        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, category);
        //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            team = new Team();
            team.setName(resultSet.getString("Name"));
            team.setName(resultSet.getString("Category"));
        }

        return team;
    }

    //TODO modificar para que busque si existe por ejemplo el DNI, LO HACEMOS PRIVATE PORQUE SOLO USAREMOS INTERNAMENTE
    private boolean existTeamAndCategory(String name, String category) throws SQLException{
        Team team = findByTeamAndCategory(name, category);
        return team != null;
    }

    //TODO modificar para que busque si existe por ejemplo el DNI, LO HACEMOS PRIVATE PORQUE SOLO USAREMOS INTERNAMENTE
    private boolean existTeam(String name) throws SQLException{
        Optional<Team> team = findByName(name);
        return team.isPresent();
    }

}
