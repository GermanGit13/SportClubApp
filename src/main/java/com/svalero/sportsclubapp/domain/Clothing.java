package com.svalero.sportsclubapp.domain;


import java.util.ArrayList;
import java.util.List;

import static com.svalero.sportsclubapp.util.Constants.PRICE;

public class Clothing {

    private boolean gameKit;
    private String dni;
    private String serigraphy;
    private int number;
    private String size;

    private List<Order> orders; //RELACIÓN N A N ENTRE LAS TABLAS ORDER Y CLOTHING - CON ESTA LISTA MANTIENE LA RELACIÓN DE LADO A LADO

    public Clothing() {
        orders = new ArrayList<>();
    }
    public Clothing(boolean gameKit, String dni, String serigraphy, int number, String size, float price) {
        this.gameKit = gameKit;
        this.dni = dni;
        this.serigraphy = serigraphy;
        this.number = number;
        this.size = size;
        price = PRICE;
        orders = new ArrayList<>();
    }

    public boolean isGameKit() {
        return gameKit;
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

    public void setName(boolean gameKit) {
        this.gameKit = gameKit;
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
