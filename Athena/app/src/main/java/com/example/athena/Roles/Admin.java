package com.example.athena.Roles;

import android.content.Context;

import java.util.ArrayList;

/**
 * This class inherits the user class and represents an administrator
 */
public class Admin extends User{

    Admin(Context context, String name, String role, ArrayList<String> privileges) {
        super(context, name, role, privileges );
    }



}
