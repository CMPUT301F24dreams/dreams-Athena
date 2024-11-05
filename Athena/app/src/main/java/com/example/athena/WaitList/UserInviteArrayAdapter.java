package com.example.athena.WaitList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.athena.Event.Event;
import com.example.athena.R;
import com.example.athena.Roles.User;
import com.example.athena.dbInfoRetrieval.DBConnector;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserInviteArrayAdapter extends ArrayAdapter<String> {
    private String userID;
    public UserInviteArrayAdapter(Context context, ArrayList<String> events, String userID){
        super(context, 0, events);
        this.userID = userID;
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
        String eventID = getItem(position);
        FirebaseFirestore db = DBConnector.getInstance().getDb();
        TextView eventTitle = view.findViewById(R.id.accept_event_title);
        TextView eventDesc = view.findViewById(R.id.accept_discription);
        String eventName = db.collection("Events").document(eventID).get().getResult().getDocumentReference("eventName").toString();
        eventTitle.setText(eventName);
        String eventDescription = db.collection("Events").document(eventID).get().getResult().getDocumentReference("description").toString();
        eventDesc.setText(String.format("you have been invited to %s. Do you wish to accept or decline the invite?", eventDescription));


        return view;
    }
}

