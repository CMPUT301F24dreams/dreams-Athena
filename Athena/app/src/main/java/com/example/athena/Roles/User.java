package com.example.athena.Roles;

import android.media.Image;

import java.util.ArrayList;

/**
 *This class represents users who have no events or
 */
public class User {
    String name;
    ArrayList<String> roles;
    String email;
    Integer phoneNumber;
    Image profilePicture;

    public User(String name, ArrayList<String> roles){
        this.name = name;
        this.roles = roles;
    }
}
