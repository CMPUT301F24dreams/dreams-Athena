package com.example.athena.Roles;
import android.content.Context;
import android.media.Image;
import com.example.athena.Event.Event;
import com.example.athena.WaitList.WaitList;

import java.util.ArrayList;


/**
 * This class Serves as a representation of the user within the application
 * it contains all of the variables which directly pertain to the user
 */
public class Entrant extends User {
    Entrant(Context context, String name, String role, ArrayList<String> privileges) {
        super(context, name, role, privileges );
    }

    }
