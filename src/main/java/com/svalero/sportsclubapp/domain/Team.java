package com.svalero.sportsclubapp.domain;

import static util.Constants.QUOTA;

public class Team {

    private String name;
    private String category;
    private String coach;
    private int maxPlayer;

    public Team(String name, String category, String coach, int maxPlayer, float quota) {
        this.name = name;
        this.category = category;
        this.coach = coach;
        this.maxPlayer = maxPlayer;
        quota = QUOTA;

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

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public float getQuota() {
        return QUOTA;
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

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }
}
