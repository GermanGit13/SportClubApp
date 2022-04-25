package com.svalero.sportsclubapp.domain;

import static com.svalero.sportsclubapp.util.Constants.QUOTA;


public class Team {

    private int id;
    private String name;
    private String category;
    private String coach;

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
    public Team(String name, String category, String coach, float quota) {
        this.name = name;
        this.category = category;
        this.coach = coach;
        quota = QUOTA;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCoach() {
        return coach;
    }

    public float getQuota() {
        return QUOTA;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }
}
