package com.svalero.sportsclubapp.dao;


import com.svalero.sportsclubapp.domain.Order;

import java.sql.Connection;

public class OrderDao {

    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void add() {

    }

    //DETALLES DE UN PEDIDO
    public Order getOrder() {
        return null;
    }

    //MARCAR PEDIDO COMO PAGADO
    public void payOrder() {

    }

}
