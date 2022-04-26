package com.svalero.sportsclubapp.dao;

import com.svalero.sportsclubapp.domain.Clothing;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClothingDao {

    private Connection connection;

    ////MEDIANTE EL CONSTRUCTOR LE PASAMOS LA CONEXIÓN PARA HABLAR CON LA BBDD
    public ClothingDao(Connection connection) {
        this.connection = connection;
    }

    //CONSTRUCTOR VACÍO PARA USARLO EN EL DAO EN LOS ARRAYLIST
    public ClothingDao() {

    }

    //Añadimos un objeto de la clase Clothing
    public void add(Clothing clothing) throws SQLException {
        //PRIMERO EL Sql, ASÍ EVITAMOS LAS INYECCIONES SQL
        String sql = "INSERT INTO clothing (serigraphy, number, size) VALUES (?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, clothing.getSerigraphy());
        statement.setInt(2, clothing.getNumber());
        statement.setString(3, clothing.getSize());
        //CUALQUIER CONSULTA QUE NO SEA UN SELECT SE LANZA CON executeUpdate. PARA SELECT USAMOS executeQuery
        statement.executeUpdate();
    }

    public boolean modify(String serigraphy, int number, Clothing clothing) throws SQLException{
        String sql = "UPDATE clothing SET serigraphy = ?, number = ?, size = ? WHERE serigrafhy = ? AND number =?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, clothing.getSerigraphy());
        statement.setInt(2, clothing.getNumber());
        statement.setString(3, clothing.getSize());
        statement.setString(4, serigraphy);
        statement.setInt(5, number);
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    public boolean delete(String serigraphy, int number, Clothing clothing) throws SQLException {
        String sql = "DELETE FROM clothing WHERE serigraphy = ? AND number = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, serigraphy);
        statement.setInt(2, number);
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    public ArrayList<Clothing> findAll() throws SQLException {
        String sql = "SELECT * FROM clothing ORDER BY serigraphy";
        ArrayList<Clothing> clothings = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Clothing clothing = new Clothing();
            clothing.setSerigraphy(resultSet.getString("serigraphy"));
            clothing.setNumber(resultSet.getInt("number"));
            clothing.setSize(resultSet.getString("talla"));
            clothings.add(clothing);
        }

        return clothings;
    }
}
