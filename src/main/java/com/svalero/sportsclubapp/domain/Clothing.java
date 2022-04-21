package com.svalero.sportsclubapp.domain;


import static com.svalero.sportsclubapp.util.Constants.PRICE;

public class Clothing {

    private String name;
    private String dni;
    private String serigraphy;
    private int number;
    private String size;

    public Clothing(String name, String dni, String serigraphy, int number, String size, float price) {
        this.name = name;
        this.dni = dni;
        this.serigraphy = serigraphy;
        this.number = number;
        this.size = size;
        price = PRICE;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
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
