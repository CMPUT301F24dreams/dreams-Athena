package com.example.athena.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.athena.Models.Event;
import com.example.athena.R;

import java.util.ArrayList;

public class EventArrayAdapter extends ArrayAdapter<Event> {
    public EventArrayAdapter (Context context, ArrayList<Event> events){
        super(context,0,events);
    }
    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.event_list_item_layout,parent,false);
        } else {
            view = convertView;
        }
        Event event = getItem(position);

        TextView eventName = view.findViewById(R.id.eventNameText);
        assert event != null;
        eventName.setText(String.format("Event Name: %s",event.getEventName()));

        ImageView image = view.findViewById(R.id.eventImage);
        Glide.with(getContext()).load(event.getImageURL()).into(image);

        return view;
    }
}
