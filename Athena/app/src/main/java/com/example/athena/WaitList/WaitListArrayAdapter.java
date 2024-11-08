package com.example.athena.WaitList;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.athena.Models.User;

import java.util.ArrayList;
//Outstanding issue- displays all user who are in the wait list for the organizer
public class WaitListArrayAdapter extends ArrayAdapter<User> {
    public WaitListArrayAdapter(Context context, ArrayList<User> user) {
        super(context, 0, user);
    }


}
