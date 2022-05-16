package com.svalero.sportsclubapp.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class DateUtils {

    private final static String DATE_PATTERN = "dd.MM.yyyy"; //CONSTANTE PARA COMO ME LLEGA EL FORMATO DE FECHA INTRODUCIDO POR EL USUARIO

    //MÉTODO PARA CONVERTIR UN LOCALDATE A PARTIR DE UN TEXTO
    public static LocalDate parseLocalDate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    //MÉTODO PARA PINTAR UN LOCALDATE POR LA PANTALLA COMO QUERAMOS DESDE EL STRING QUE NOS DA
    public static String formatLocalDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }


    //MÉTODO PARA CONVERTIR UN LOCALDATE A DATE
    public static java.util.Date toDate(LocalDate localDate) {
        return java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    //MÉTODO PARA CONVERTIR UN DATE  A LOCALDATE
    public static LocalDate toLocalDate(java.util.Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    //MÉTODO PARA CONVERTIR UN DATESQL A LOCALDATE
    public static LocalDate toLocalDate(java.sql.Date date){
        return toLocalDate(toUtilDate(date));
    }

    //MÉTODO PARA CONVERTIR DE LOCALDATE A SQLDATE
    public static java.sql.Date toSqlDate(LocalDate localDate) {
        return new java.sql.Date(toDate(localDate).getTime()); //APROVECHAMOS EL MÉTODO toDate
    }

    //MÉTODO PARA CONVERTIR DE UTIL DATE A SQL DATE
    public static java.sql.Date toSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    //MÉTODO PARA CONVERTIR SQL a DATE
    public static java.util.Date toUtilDate(java.sql.Date date) {
        return new java.util.Date(date.getTime());
    }

}
