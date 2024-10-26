package com.example.athena.Roles;

import java.util.ArrayList;


/**
 * This class Serves as a representation of an entrant within the application
 * it contains all of the variables which directly pertain to the entrant
 */
public class NonAdminUser extends User {

    private ArrayList<String> roles;//mandatory role field
    private String status;//status that is labelled for the entrants (Indicated their notification status)
    private Integer phoneNumber;//Optional phone number field

    public NonAdminUser(String name, ArrayList<String> roles) {
        super(name, roles);
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPhoneNumber(){
        return phoneNumber;
    }
}
