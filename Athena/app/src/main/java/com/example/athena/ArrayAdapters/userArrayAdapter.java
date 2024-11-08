/*
 * This class is a custom ArrayAdapter for displaying user information in a ListView.
 * It is responsible for binding a list of User objects to a list item layout, which includes the user's name, email, and phone number.
 * The user data is populated into the corresponding TextViews within each list item.
 */
package com.example.athena.ArrayAdapters;

import android.content.Context;
import android.util.Log;
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
import com.example.athena.Models.User;
import com.example.athena.R;

import java.util.ArrayList;

public class userArrayAdapter extends ArrayAdapter<User> {
    public userArrayAdapter(Context context, ArrayList<User> users){
        super(context,0, users);
    }
    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item_layout,parent,false);
        } else {
            view = convertView;
        }

        User user = getItem(position);

        TextView nameText = view.findViewById(R.id.userName);
        assert user != null;
        nameText.setText(user.getName());

        TextView emailText = view.findViewById(R.id.emailText);
        emailText.setText(user.getEmail());

        TextView phoneText = view.findViewById(R.id.phoneText);
        phoneText.setText(user.getPhone());

        return view;
    }
}
