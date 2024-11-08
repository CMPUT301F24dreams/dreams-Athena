package com.example.athena.Models;

import com.example.athena.Interfaces.Model;
import com.example.athena.Interfaces.Observer;

import java.util.ArrayList;
import java.util.List;

public class User implements Model { // TO-DO Java-doc
    private String name;
    private String email;
    private String phone;
    private ArrayList<Event> Events;

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
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

}