package com.example.athena.ArrayAdapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.athena.Interfaces.Model;
import com.example.athena.Interfaces.Observer;
import com.example.athena.Models.Event;

/**
 * Adapter class for displaying event details in a view.
 */
public class EventViewAdapter implements Observer {
    private Context context;
    private TextView eventName;
    private TextView eventDesc;
    private ImageView imageView;
    private TextView regStart;
    private TextView regEnd;

    /**
     * Updates the view with the event details.
     *
     * @param model The event model containing the details.
     */
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

    /**
     * Constructor for the EventViewAdapter.
     *
     * @param context The context in which the adapter is used.
     */
    public EventViewAdapter(Context context) {
        this.context = context;
    }

    /**
     * Sets the TextView for the event name.
     *
     * @param eventName The TextView for the event name.
     */
    public void setEventName(TextView eventName) {
        this.eventName = eventName;
    }

    /**
     * Sets the ImageView for the event image.
     *
     * @param imageView The ImageView for the event image.
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     * Sets the TextView for the registration start date.
     *
     * @param regStart The TextView for the registration start date.
     */
    public void setRegStart(TextView regStart) {
        this.regStart = regStart;
    }

    /**
     * Sets the TextView for the registration end date.
     *
     * @param regEnd The TextView for the registration end date.
     */
    public void setRegEnd(TextView regEnd) {
        this.regEnd = regEnd;
    }

    /**
     * Sets the TextView for the event description.
     *
     * @param eventDesc The TextView for the event description.
     */
    public void setEventDesc(TextView eventDesc) {
        this.eventDesc = eventDesc;
    }
}
