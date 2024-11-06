package com.example.athena.WaitList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.athena.Models.Event;
import com.example.athena.R;


import java.util.ArrayList;

public class UserInviteArrayAdapter extends ArrayAdapter<Event> {
    public UserInviteArrayAdapter(Context context, ArrayList<Event> events){
        super(context, 0, events);
    }
    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.invited_list_content,parent,false);
        } else {
            view = convertView;
        }
        Event event = getItem(position);
        TextView eventTitle = view.findViewById(R.id.accept_event_title);
        String eventName = event.getEventName();
        eventTitle.setText(eventName);

        return view;
    }
}

