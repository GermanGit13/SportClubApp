package com.svalero.sportsclubapp.dao;


import com.svalero.sportsclubapp.domain.Clothing;
import com.svalero.sportsclubapp.domain.Order;
import com.svalero.sportsclubapp.domain.User;
import com.svalero.sportsclubapp.util.DateUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDao {

    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void add(User user, List<Clothing> clothings) throws SQLException {
        String orderSql = "INSERT INTO orders (code, date, id_users) VALUES (?, ?, ?)";

        //DEJAMOS EN FALSO QUE MARCA LA TRANSACCIÓN COMO OK
        connection.setAutoCommit(false);

        PreparedStatement orderStatement = connection.prepareStatement(orderSql,
                PreparedStatement.RETURN_GENERATED_KEYS);// PARA PEDIR LA CLAVE GENERADA Y ASI PODER O NO RECOGERLA, EN ESTE CASO EN EL ORDERID
        orderStatement.setString(1, UUID.randomUUID().toString()); //GENERA UN NUMERO ALEATORIO PARA ASIGNÁRSELO A LA ORDEN DE PEDIDO
        orderStatement.setDate(2, new Date(System.currentTimeMillis())); //ASIGNA LA FECHA DEL SISTEMA EN ELP MOMENTO DE REALIZAR EL PEDIDO
        orderStatement.setInt(3, user.getIdUser()); //ASIGNA EL ID DEL USUARIO A LA ORDEN DE PEDIDO
        orderStatement.executeUpdate();

        //OBTENER EL ORDERID GENERADO EN LA SENTENCIA ANTERIOR - ÚLTIMO AUTO_INCREMENT GENERADO
            ResultSet orderKeys = orderStatement.getGeneratedKeys();
        orderKeys.next();
        int orderId = orderKeys.getInt(1);
        orderKeys.close();

        for (Clothing clothing : clothings) { //BUCLE POR CADA LINEA DE PEDIDO POR SI TIENEN VARIOS HIJOS Y PIDEN VARIAS EQUIPACIONES
            String clothingSql = "INSERT INTO orders_clothings (order_id, clothing_id) VALUES (?, ?,)";

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

    //DETALLES DE UN PEDIDO ENTRE DOS FECHAS
    public List<Order> getOrdersBetweenDates(LocalDate fromDate, LocalDate toDate) throws SQLException{
        String sql = "SELECT * FROM orders ORDER WHERE  date BETWEEN ? AND ?";
        List<Order> orders = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, DateUtils.toSqlDate(fromDate)); //CONVERTIMOS EL TIEMPO JAVA EN TIEMPO SQL
        statement.setDate(2, DateUtils.toSqlDate(toDate));
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getInt(1));
            order.setCode(resultSet.getString(2));
            order.setPaid(resultSet.getBoolean(3));
            order.setDate(DateUtils.toLocalDate(resultSet.getDate(4))); //USAMOS DateUtils.toLocalDate para el cambio de fechas de sql a LocalDate
            orders.add(order);
        }

        return orders;
    }
}
