package com.example.athena.Roles;

import android.content.Context;

import java.util.ArrayList;

public class User {
    Context context;
    String name;
    String role;
    ArrayList<String> privileges;


    User(Context context, String name, String role, ArrayList<String> privileges){
        this.context = context;
        this.name = name;
        this.role = role;
        this.privileges = privileges;
    }
}
