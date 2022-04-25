package com.svalero.sportsclubapp.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int id;
    private String code;
    private boolean paid;
    private LocalDate date;

    private List<Clothing> clothings; //RELACIÓN N A N ENTRE LAS TABLAS ORDER Y CLOTHING - CON ESTA LISTA MANTIENE LA RELACIÓN DE LADO A LADO
    private User user;

    public Order() {
        clothings = new ArrayList<>();
    }

    public Order(String code, boolean paid, LocalDate date, User user) {
        this.code = code;
        this.paid = paid;
        this.date = date;
        this.user = user;
        clothings = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public boolean isPaid() {
        return paid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
