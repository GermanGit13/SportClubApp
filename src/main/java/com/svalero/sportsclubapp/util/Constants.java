package com.svalero.sportsclubapp.util;

public class Constants {

    public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    //TODO Cambiar el nombre de la BBDD
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/library"; //máquina donde está la BBDD puerto donde escucha y la BBDD
    public static final String ORACLE_URL = "jdbc:oracle:thin:@//localhost:1521/xe"; //máquina donde está la BBDD puerto donde escucha y la BBDD

    public static final String USERNAME = "CBSMV";
    public static final String PASSWORD = "1234";

    public static final float PRICE = 45.50f;
    public static final float QUOTA = 225.50f;
}
