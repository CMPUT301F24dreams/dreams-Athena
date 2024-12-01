/**
 * This class is a custom ArrayAdapter designed to bind event data to a ListView. It displays a list of Event objects by inflating a custom layout for each item, which includes the event name and image.
 * The event name is displayed in a TextView, while the event image is loaded into an ImageView using the Glide library.
 */
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
    public EventArrayAdapter(Context context, ArrayList<Event> events){
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

        TextView eventName = view.findViewById(R.id.userName);
        assert event != null;
        eventName.setText(event.getEventName());


        ImageView image = view.findViewById(R.id.userImage);
        Glide.with(getContext()).load(event.getImageURL()).into(image);

        return view;
    }
}
