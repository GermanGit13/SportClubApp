package com.svalero.sportsclubapp.domain;

import javax.swing.*;

import static com.svalero.sportsclubapp.util.Constants.QUOTA;


public class Team {

    private int id;
    private int idUser;
    private String name;
    private String category;

    private User user; //PARA RELACIONAR LOS ENTRENADORES

    //MÉTODO PARA PRUEBAS
    public Team(String name, String category, float quota) {
        this.name = name;
        this.category = category;
        quota = QUOTA;
    }

    //MÉTODO PARA BUSCAR POR CATEGORY
    public Team(String name, String category) {
        this.name = name;
        this.category = category;
    }

    //CONSTRUCTOR VACÍO PARA USARLO EN EL DAO EN LOS ARRAYLIST
    public Team() {

    }

    public Team(String name, String category, User user, float quota) {
        this.name = name;
        this.category = category;
        this.user = user;
        quota = QUOTA;
    }

    public int getId() {
        return id;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public float getQuota() {
        return QUOTA;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
