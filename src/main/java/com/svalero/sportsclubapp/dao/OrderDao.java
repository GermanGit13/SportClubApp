package com.svalero.sportsclubapp.dao;


import com.svalero.sportsclubapp.domain.Clothing;
import com.svalero.sportsclubapp.domain.Order;
import com.svalero.sportsclubapp.domain.User;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class OrderDao {

    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void add(User user, List<Clothing> clothings) throws SQLException {
        String orderSql = "INSERT INTO order (date, code, id_users) VALUES (?, ?, ?)";

        //DEJAMOS EN FALSO QUE MARCA LA TRANSACCIÓN COMO OK
        connection.setAutoCommit(false);

        PreparedStatement orderStatement = connection.prepareStatement(orderSql,
                PreparedStatement.RETURN_GENERATED_KEYS);// PARA PEDIR LA CLAVE GENERADA Y ASI PODER O NO RECOGERLA, EN ESTE CASO EN EL ORDERID
        orderStatement.setDate(1, new Date(System.currentTimeMillis())); //ASIGNA LA FECHA DEL SISTEMA EN ELP MOMENTO DE REALIZAR EL PEDIDO
        orderStatement.setString(2, UUID.randomUUID().toString()); //GENERA UN NUMERO ALEATORIO PARA ASIGNÁRSELO A LA ORDEN DE PEDIDO
        orderStatement.setInt(3, user.getId()); //ASIGNA EL ID DEL USUARIO A LA ORDEN DE PEDIDO
        orderStatement.executeUpdate();

        //OBTENER EL ORDERID GENERADO EN LA SENTENCIA ANTERIOR - ÚLTIMO AUTO_INCREMENT GENERADO
            ResultSet orderKeys = orderStatement.getGeneratedKeys();
        orderKeys.next();
        int orderId = orderKeys.getInt(1);
        orderKeys.close();

        for (Clothing clothing : clothings) { //BUCLE POR CADA LINEA DE PEDIDO POR SI TIENEN VARIOS HIJOS Y PIDEN VARIAS EQUIPACIONES
            String clothingSql = "INSERT INTO order_clothing (order_id, clothing_id) VALUES (?, ?,)";

            PreparedStatement clothingStatement = connection. prepareStatement(clothingSql);
            clothingStatement.setInt(1, orderId);
            clothingStatement.setInt(2, clothing.getId());
        }

        connection.commit(); //PARA CONFIRMAR LA TRANSACCIÓN
        connection.setAutoCommit(true); //PONEMOS A TRUE DE NUEVO EL AUTOCOMMIT
      }

    //DETALLES DE UN PEDIDO
    public Order getOrder() {
        return null;
    }

    //MARCAR PEDIDO COMO PAGADO, idem para marcar coach
    public void payOrder() {

    }



}
