package com.example.athena.Models;

import java.util.ArrayList;

public class User { // TO-DO Java-doc
    private String name;
    private String email;
    private String phone;
    private String imageURL;
    private ArrayList<Event> Events;

    public User(String name, String email, String phone, String imageURL) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Event> getEvents() {
        return Events;
    }
    public void addEvent(Event event) {
        this.Events.add(event);
    }
    public String getImageURL() {
        return imageURL;
    }
}