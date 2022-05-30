package com.svalero.sportsclubapp.domain;

import static com.svalero.sportsclubapp.util.Constants.QUOTA;


public class Team {

    private int idTeam;
    private String idUser;
    private String name;
    private String category;

    /**
     * User: PARA RELACIONAR LOS ENTRENADORES
     */
    private User user;

    /**
     * MÉTODO PARA BUSCAR POR CATEGORY
     */
    public Team(String name, String category) {
        this.name = name;
        this.category = category;
    }

    /**
     * CONSTRUCTOR VACÍO PARA USARLO EN EL DAO EN LOS ARRAYLIST
     */
    public Team() {

    }

    public Team(String name, String category, User user, float quota) {
        this.name = name;
        this.category = category;
        this.user = user;
        quota = QUOTA;
    }

    public Team(String name, String category, float quota, String idUser) {
        this.name = name;
        this.category = category;
        quota = QUOTA;
        this.idUser = idUser;
    }

    public Team(String name, String category, String idUser) {
        this.name = name;
        this.category = category;
        this.idUser = idUser;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public String getIdUser() {
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

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public void setIdUser(String  idUser) {
        this.idUser = idUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuota(float quota) {
    }
}
