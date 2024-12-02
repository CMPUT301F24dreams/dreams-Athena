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
    private TextView eventDesc;
    private ImageView imageView;
    private TextView regStart;
    private TextView regEnd;

    @Override
    public void update(Model model) {
        Event event = (Event) model;
        eventDesc.setText(event.getEventDescription());
        regStart.setText(event.getStartReg());
        regEnd.setText(event.getEndReg());
        eventName.setText(event.getEventName());
        eventDesc.setText(event.getEventDescription());
        Glide.with(context).load(event.getImageURL()).into(imageView);
    }

    public EventViewAdapter(Context context) {
        this.context = context;
    }

    public void setEventName(TextView eventName) {
        this.eventName = eventName;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setRegStart(TextView regStart){
        this.regStart = regStart;
    }

    public void setRegEnd(TextView regEnd){
        this.regEnd = regEnd;
    }
    public void setEventDesc(TextView eventDesc){
        this.eventDesc = eventDesc;
    }

}
