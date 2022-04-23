package com.svalero.sportsclubapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.svalero.sportsclubapp.util.Constants.*;


public class Database {

    //Connection es un objeta JAVA usado para las conexiones. Usamos esta clase para crear la conexión y cerrar la misma
    private Connection connection;

    public Connection getConnection() {
        try {
            Class.forName(ORACLE_DRIVER); //con Class.forName cargamos el driver para conectarnos a la BBDD
            connection = DriverManager.getConnection(ORACLE_URL, USERNAME, PASSWORD);
            System.out.println("Conectado!");
        } catch (ClassNotFoundException cnfe) {  //cnfe es una abreviatura o alias para luego referirlo que damos nosotros de ClassNotFoundException
            System.out.println("No se ha podido cargar el driver de conexión. Verifique que los drivers están disponibles");
            cnfe.printStackTrace();  //Para obtener las trazas de la Excepción
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //Para obtener las trazas de la Excepción
        }

        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace(); //Para obtener las trazas de la Excepción

        }
    }
}
