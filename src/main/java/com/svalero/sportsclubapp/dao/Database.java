package com.svalero.sportsclubapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.svalero.sportsclubapp.util.Constants.*;


public class Database {

    //Connection OBJETO JAVA USADO PARA LAS CONEXIONES. LO USAMOS PARA CONECTAR Y CERRAR LA CONEXION CON LA BBDD
    private Connection connection;

    public Connection getConnection() {
        try {
            Class.forName(ORACLE_DRIVER); //CON Class.forName CARGAMOS EL DRIVER PARA CONECTAR CON LA BBDD
            connection = DriverManager.getConnection(ORACLE_URL, USERNAME, PASSWORD);
            System.out.println("Conectado!");
        } catch (ClassNotFoundException cnfe) {  //cnfe ABREVIATURA QUE PROPORCIONAMOS NOSOTROS PARA ClassNotFoundException
            System.out.println("No se ha podido cargar el driver de conexión. Verifique que los drivers están disponibles");
            cnfe.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCION Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();  //PARA OBTENER LAS TRAZAS DE LA EXCEPCION Y ASI LUEGO SEGUIR CON PRECISION EL ERROR
        }

        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace(); //PARA OBTENER LAS TRAZAS DE LA EXCEPCION Y ASI LUEGO SEGUIR CON PRECISION EL ERROR

        }
    }
}
