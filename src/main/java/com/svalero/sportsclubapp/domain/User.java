package com.svalero.sportsclubapp.domain;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String dni;
    private String username;
    private String pass;
    private String coach;

    public User() {

    }

    //CONSTRUCTOR PARA PODER BUSCAR SI EXISTE EL USERNAME AL CREARLO
    public User(String username) {
        this.username = username;
    }

    //TODO REVISAR COMO HACER COACH A LOS USUARIOS
    public User(String firstName, String lastName, String email, String dni, String username, String pass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dni = dni;
        this.username = username;
        this.pass = pass;
    }
    public User(String firstName, String lastName, String email, String dni, String coach) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dni = dni;
        this.coach = coach;
    }
    public int getIdUser() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDni() {
        return dni;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public String getCoach() {
        return coach;
    }

    public void setIdUser(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }
}


