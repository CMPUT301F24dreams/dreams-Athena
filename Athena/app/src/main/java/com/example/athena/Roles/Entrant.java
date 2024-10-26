package com.example.athena.Roles;
import android.content.Context;

import java.util.ArrayList;


/**
 * This class Serves as a representation of an entrant within the application
 * it contains all of the variables which directly pertain to the entrant
 */
public class Entrant extends User {
    private String role;
    private String status;

    public Entrant(String name, ArrayList<String> roles, String status) {
        super(name, roles);
        this.status = status;
    }


    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
