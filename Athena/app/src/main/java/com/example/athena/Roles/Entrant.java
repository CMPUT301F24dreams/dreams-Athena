package com.example.athena.Roles;
import android.content.Context;

import java.util.ArrayList;


/**
 * This class Serves as a representation of the user within the application
 * it contains all of the variables which directly pertain to the user
 */
public class Entrant extends User {
    private boolean receiveNotification;

    Entrant(Context context, String name, String role, ArrayList<String> privileges) {
        super(context, name, role, privileges );
        receiveNotification = Boolean.TRUE;
    }

    public void setReceiveNotification(boolean receiveNotification) {
        this.receiveNotification = receiveNotification;
    }

    public boolean isReceiveNotification() {
        return receiveNotification;
    }
}
