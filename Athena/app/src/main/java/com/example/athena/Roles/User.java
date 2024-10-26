package com.example.athena.Roles;

import java.util.ArrayList;

public class User {
    String name;
    ArrayList<String> roles;


    User( String name, ArrayList<String> roles){
        this.name = name;
        this.roles = roles;
    }
}
