package com.example.athena.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        TextView eventTitle = view.findViewById(R.id.event_title_textview_list);
        TextView eventDisc = view.findViewById(R.id.event_description_textview_list);
        TextView eventRegStart = view.findViewById(R.id.registration_start_date_textview);
        TextView eventRegDead = view.findViewById(R.id.reg_deadline_text_view_list);

        eventTitle.setText(event.getEventName());
        eventDisc.setText(event.getDescription());
        eventRegStart.setText(event.getRegStart());
        eventRegDead.setText(event.getRegEnd());
        return view;
    }
}
