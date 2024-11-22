package com.example.athena.ArrayAdapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.athena.Interfaces.Model;
import com.example.athena.Interfaces.Observer;
import com.example.athena.Models.Event;

public class EventViewAdapter implements Observer {
    private Context context;
    private TextView eventName;
    private ImageView imageView;

    public EventViewAdapter(Context context) {
        this.context = context;
    }

    public void setEventName(TextView eventName) {
        this.eventName = eventName;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void update(Model model) {
        Event event = (Event) model;

        eventName.setText(event.getEventName());
        Glide.with(context).load(event.getImageURL()).into(imageView);
    }
}
