package com.svalero.sportsclubapp.domain;


import java.util.ArrayList;
import java.util.List;

import static com.svalero.sportsclubapp.util.Constants.PRICE;

public class Clothing {

    private int id;
    private String dni;
    private String serigraphy;
    private int number;
    private String size;

    /**
     * RELACIÓN N A N ENTRE LAS TABLAS ORDER Y CLOTHING - CON ESTA LISTA MANTIENE LA RELACIÓN DE LADO A LADO
     */
    private List<Order> orders;

    public Clothing() {
        orders = new ArrayList<>();
    }

    public Clothing(String dni,String serigraphy, int number, String size, float price) {
        this.dni = dni;
        this.serigraphy = serigraphy;
        this.number = number;
        this.size = size;
        price = PRICE;
        orders = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getSerigraphy() {
        return serigraphy;
    }

    public int getNumber() {
        return number;
    }

    public String getSize() {
        return size;
    }

    public float getPrice() {
        return PRICE;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setSerigraphy(String serigraphy) {
        this.serigraphy = serigraphy;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
