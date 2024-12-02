/*
 * This class is a custom ArrayAdapter for displaying user information in a ListView.
 * It is responsible for binding a list of User objects to a list item layout, which includes the user's name, email, and phone number.
 * The user data is populated into the corresponding TextViews within each list item.
 */
package com.example.athena.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.athena.Models.Event;
import com.example.athena.Models.User;
import com.example.athena.R;

import java.util.ArrayList;

/**
 * Custom ArrayAdapter for displaying user information in a ListView.
 */
public class UserArrayAdapter extends ArrayAdapter<User> {

    /**
     * Constructor for the UserArrayAdapter.
     *
     * @param context The context in which the adapter is used.
     * @param users The list of users to display.
     */
    public UserArrayAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    /**
     * Provides a view for an AdapterView.
     *
     * @param position The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item_layout, parent, false);
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
