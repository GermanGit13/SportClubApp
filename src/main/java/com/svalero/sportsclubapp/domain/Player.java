package com.svalero.sportsclubapp.domain;

public class Player {

    private String firstName;
    private String lastName;
    private int number;
    private int yearOfBirth;
    private String dni;

    private Team team; //PARA RELACIONAR LOS EQUIPOS CON LOS JUGADORES

    public Player() {

    }

    public Player(String firstName, String lastName, int number, int yearOfBirth, String dni, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.yearOfBirth = yearOfBirth;
        this.dni = dni;
        this.team = team;
    }

    public Player(String firstName, String lastName, int number, int yearOfBirth, String dni) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.yearOfBirth = yearOfBirth;
        this.dni = dni;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getNumber() {
        return number;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getDni() {
        return dni;
    }

    public Team getTeam() {
        return team;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
