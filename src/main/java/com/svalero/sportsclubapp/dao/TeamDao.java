package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.Team;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Optional;

public class TeamDao {

    private Connection connection;

    //MEDIANTE EL CONSTRUCTOR LE PASAMOS LA CONEXION PARA HABLAR CON LA BBDD
    public TeamDao(Connection connection) {
        this.connection = connection;
    }

    //AÑADIMOS UN OBJETO DE LA CLASE TEAM
    public void add(Team team) {
        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "INSERT INTO team (name, category, QUOTA) VALUES (?, ?, ?)";
        try {
            //COMPONER EL SQL CON PreparedStatement EN BASE A LA SENTENCIA sql
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, team.getName());
            statement.setString(2, team.getCategory());
            statement.setFloat(3, team.getQuota());
            //CUALQUIER CONSULTA QUE NO SEA UN SELECT SE LA CON executeUpdate PARA SELECT USAMOS executeQuery
            statement.executeUpdate();
            } catch (SQLException sqle){
                System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
                sqle.printStackTrace();  //Para obtener las trazas de la Excepción
            }
        }

    //LE PASAMOS QUE NOMBRE QUIERO MODIFICAR EL OBJETO PARA A MODIFICAR
    public boolean modify(String name, Team team) {
        String sql = "UPDATE TEAM SET NAME = ?, CATEGORY = ? WHERE NAME = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, team.getName());
            statement.setString(2, team.getCategory());
            statement.setString(3, name);
            //PARA DECIRNOS EL NÚMERO DE FILAS QUE HA BORRADO
            int rows = statement.executeUpdate();
            //DEVUELVE TRUE SI LAS FILAS AFECTADAS SON IGUAL A 1 SINO CONTINUA HASTA EL FALSE
            return rows ==1;
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //Para obtener las trazas de la Excepción
        }

        return false;
    }

    public boolean delete(String name) {
        String sql = "DELETE FROM TEAM WHERE name = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            //PARA DECIRNOS EL NÚMERO DE FILAS QUE HA BORRADO
            int rows = statement.executeUpdate();
            //DEVUELVE TRUE SI LAS FILAS AFECTADAS SON IGUAL A 1 SINO CONTINUA HASTA EL FALSE
            return rows ==1;
        } catch (SQLException sqle){
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //Para obtener las trazas de la Excepción
        }
        //SI DEVUELVE FALSE SI NO SE HA BORRADO NINGUNA FILA
        return false;
    }

    public ArrayList<Team> findAll() {
        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "SELECT * FROM TEAM";
        ArrayList<Team> teams = new ArrayList<>();
        try {
            //COMPONER EL SQL CON PreparedStatement EN BASE A LA SENTENCIA sql
            PreparedStatement statement = connection.prepareStatement(sql);
            //ResultSet ESPECIE DE ARRAYLIST CURSOR QUE APUNTE AL CONTENIDO CARGADO EN LA MEMORIA JAVA DONDE METEMOS EL RESULTADO DE statement.executeQuery
            ResultSet resultSet = statement.executeQuery();
            //RECORREMOS EL RESULTSET
            while (resultSet.next()) {
                Team team = new Team();
                team.setName(resultSet.getString("Name"));
                team.setCategory(resultSet.getString("Category"));
                //TODO REVISAR COMO IMPRIMIR LA QUOTA
                teams.add(team);
            }
            return teams;
        } catch (SQLException sqle){
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //Para obtener las trazas de la Excepción
        }
        //SI LLEGA HASTA AQUÍ ES QUE SE HA SALTADO EL TRY Y DEVUELVE EL ARRAYLIST VACIO
        return teams;
    }

    //TODO REVISAR FALLO DE OPTIONAL
    /*
    public Optional<Team> findByCategory(String category) throws SQLException {
        String sql = "SELECT * FROM TEAM WHERE category = ?";
        Team team = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, category);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            team = new Team();
            team.setName(resultSet.getString("Name"));
            team.setCategory(resultSet.getString("Category"));
            //TODO REVISAR COMO IMPRIMIR LA QUOTA
        }

        return Optional.ofNullable(team);
    } */
}
