package com.example.athena.Interfaces;

public interface Model {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers();
}
