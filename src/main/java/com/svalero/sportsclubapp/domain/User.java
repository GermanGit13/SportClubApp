package com.svalero.sportsclubapp.domain;

import java.time.LocalDate;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String dni;
    private String username;
    private String password;
    private boolean coach;

    public User() {

    }

    public User(String firstName, String lastName, String email, String dni, String username, String password, boolean coach) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dni = dni;
        this.username = username;
        this.password = password;
        this.coach = coach;
    }

    public int getId() {
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

    public String getPassword() {
        return password;
    }

    public boolean isCoach() {
        return coach;
    }

    public void setId(int id) {
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCoach(boolean coach) {
        this.coach = coach;
    }
}


