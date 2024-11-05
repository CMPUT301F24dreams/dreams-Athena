package com.example.athena.Interfaces;

import com.example.athena.Interfaces.Observer;

public interface Model {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers();
}
