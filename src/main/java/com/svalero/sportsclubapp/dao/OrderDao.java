package com.svalero.sportsclubapp.dao;


import com.svalero.sportsclubapp.domain.Clothing;
import com.svalero.sportsclubapp.domain.Order;
import com.svalero.sportsclubapp.domain.User;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class OrderDao {

    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void add(User user, List<Clothing> clothings) {
        String sql = "INSERT INTO order (date, paid, code, id_users) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setStrin;

        //DAMOS DE ALTA EN UNA SEGUNDA TABLA
        String sql2 = "INSERT INTO orders_clothing (date, paid, code, id_users) VALUES (?, ?, ?, ?)";

        PreparedStatement statement2 = connection.prepareStatement(sql2);


    }

    //DETALLES DE UN PEDIDO
    public Order getOrder() {
        return null;
    }

    //MARCAR PEDIDO COMO PAGADO, idem para marcar coach
    public void payOrder() {

    }



}
