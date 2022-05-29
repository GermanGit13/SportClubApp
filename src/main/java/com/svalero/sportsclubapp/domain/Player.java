package com.svalero.sportsclubapp.domain;

public class Player {

    private int idPlayer;
    private int idTeam;
    private int idUser;
    private String firstName;
    private String lastName;
    private int number;
    private int yearOfBirth;
    private String dni;

    /**
     * Team: PARA RELACIONAR LOS EQUIPOS CON LOS JUGADORES
     * User: PARA RELACIONAR LOS JUGADORES CON LOS PADRES
     */
    private Team team;
    private User user;

    public Player() {

    }

    public Player(String firstName, String lastName, int number, int yearOfBirth, String dni, Team team, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.yearOfBirth = yearOfBirth;
        this.dni = dni;
        this.team = team;
        this.user = user;
    }

    public Player(String firstName, String lastName, int number, int yearOfBirth, String dni) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.yearOfBirth = yearOfBirth;
        this.dni = dni;
    }

    public Player(String firstName, String lastName, int number, int yearOfBirth, String dni, int idUser) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.yearOfBirth = yearOfBirth;
        this.dni = dni;
        this.idUser = idUser;
    }

    public int getIdPlayer() {
        return idPlayer;
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

    public int getIdTeam() {
        return idTeam;
    }

    public int getIdUser() {
        return idUser;
    }

    public User getUser() {
        return user;
    }

    public void setIdPlayer(int id) {
        this.idPlayer = id;
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

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


