package com.gdgcatania.info.studyjamattendance.object;

import java.util.ArrayList;

/**
 * Created by Andrea on 05/02/2015.
 */
public class User {

    private int id;
    private String surname;
    private String name;
    private String email;
    private ArrayList<Lesson> lessons;

    public User(int id, String surname, String name, String email) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }
}