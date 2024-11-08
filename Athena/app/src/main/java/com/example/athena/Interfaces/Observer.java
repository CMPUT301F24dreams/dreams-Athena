package com.example.athena.Interfaces;
/**
 * Interface representing an observer in the observer pattern, which is notified when the model changes.
 */
import com.example.athena.Interfaces.Model;
public interface Observer {
    public void update(Model model);
}
